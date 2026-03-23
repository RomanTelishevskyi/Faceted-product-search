import {Component, OnInit, ChangeDetectorRef} from '@angular/core';
import {ActivatedRoute, Router, RouterOutlet} from '@angular/router';
import {Product} from './product/product';
import {CategoriesService, Category} from './services/categories.service';
import {FilterItem} from './filter-item/filter-item';
import {BrandService, Brand} from './services/brand.service';
import {ProductDTO, ProductService} from './services/product.service';
import {FacetService, Facet} from './services/facet.service';

@Component({
  selector: 'app-root',
  imports: [Product, FilterItem, RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  allCategories: Category[] = [];
  allBrands: Brand[] = [];
  facetCounts: Facet[] = [];
  categories: Category[] = [];
  brands: Brand[] = [];
  products: ProductDTO[] = [];
  selectedCategoryIds: number[] = [];
  selectedBrandIds: number[] = [];
  appliedCategoryIds: number[] = [];
  appliedBrandIds: number[] = [];
  name = '';
  showAllCategories = false;
  showAllBrands = false;
  loading = false;
  loadingMore = false;
  offset = 0;
  hasMoreProducts = true;

  constructor(
    private categoriesService: CategoriesService,
    private brandService: BrandService,
    private productService: ProductService,
    private facetService: FacetService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {
  }

  ngOnInit() {
    this.categoriesService.getCategories().subscribe({
      next: (data) => {
        this.allCategories = data;
        this.categories = data;
        this.restoreStateFromUrl();
      },
      error: (error) => console.error('[App] Failed to load categories:', error)
    });

    this.brandService.getBrands().subscribe({
      next: (data) => {
        this.allBrands = data;
        this.brands = data;
        this.restoreStateFromUrl();
      },
      error: (error) => console.error('[App] Failed to load brands:', error)
    });
  }

  private restoreStateFromUrl() {
    if (!this.allCategories.length || !this.allBrands.length) {
      return;
    }

    const categoryIdsParam = this.route.snapshot.queryParamMap.get('categoryIds') ?? '';
    const brandIdsParam = this.route.snapshot.queryParamMap.get('brandIds') ?? '';
    const partialNameParam = this.route.snapshot.queryParamMap.get('partialName') ?? '';

    this.selectedCategoryIds = categoryIdsParam
      .split(',')
      .filter(Boolean)
      .map(Number)
      .filter(id => !Number.isNaN(id));

    this.selectedBrandIds = brandIdsParam
      .split(',')
      .filter(Boolean)
      .map(Number)
      .filter(id => !Number.isNaN(id));

    this.appliedCategoryIds = [...this.selectedCategoryIds];
    this.appliedBrandIds = [...this.selectedBrandIds];
    this.name = partialNameParam;

    this.resetResults();
    this.loadData();
  }

  onSearchInput(event: Event) {
    const input = event.target as HTMLInputElement;
    this.name = input.value;
    this.resetResults();
    this.loadData();
  }

  loadMore() {
    if (this.loadingMore || !this.hasMoreProducts) {
      return;
    }

    this.loadingMore = true;
    this.loadProductsPage(true);
  }

  private resetResults() {
    this.products = [];
    this.offset = 0;
    this.hasMoreProducts = true;
  }

  private updateUrlFromState() {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {
        categoryIds: this.appliedCategoryIds.length ? this.appliedCategoryIds.join(',') : null,
        brandIds: this.appliedBrandIds.length ? this.appliedBrandIds.join(',') : null,
        partialName: this.name.trim() ? this.name.trim() : null
      },
      queryParamsHandling: 'merge',
      replaceUrl: true
    });
  }

  private rebuildAvailableFiltersFromFacets(facets: Facet[]) {
    this.facetCounts = facets;

    const brandCounts = new Map<number, number>();
    const categoryCounts = new Map<number, number>();

    for (const facet of facets) {
      if (facet.facetType === 'brand') {
        brandCounts.set(facet.facetId, facet.productCount);
      } else if (facet.facetType === 'category') {
        categoryCounts.set(facet.facetId, facet.productCount);
      }
    }

    this.brands = this.allBrands.filter(brand => brandCounts.has(brand.id));
    this.categories = this.allCategories.filter(category => categoryCounts.has(category.id));

    this.selectedBrandIds = this.selectedBrandIds.filter(id =>
      this.brands.some(brand => brand.id === id)
    );
    this.selectedCategoryIds = this.selectedCategoryIds.filter(id =>
      this.categories.some(category => category.id === id)
    );

    this.appliedBrandIds = [...this.selectedBrandIds];
    this.appliedCategoryIds = [...this.selectedCategoryIds];
  }

  private loadProductsPage(append: boolean) {
    const partialName = this.name.trim();

    this.productService
      .getProducts(this.appliedCategoryIds, this.appliedBrandIds, partialName, this.offset)
      .subscribe({
        next: (data) => {
          this.products = append ? [...this.products, ...data] : data;
          this.offset += data.length;
          this.hasMoreProducts = data.length === 50;
          this.loading = false;
          this.loadingMore = false;
          this.cdr.detectChanges();
        },
        error: (error) => {
          console.error('[App] Failed to load products:', error);
          this.loading = false;
          this.loadingMore = false;
          this.cdr.detectChanges();
        }
      });
  }

  loadData() {
    this.loading = true;
    this.loadingMore = false;
    this.updateUrlFromState();

    this.loadProductsPage(false);

    const partialName = this.name.trim();
    this.facetService.getFacets(this.appliedCategoryIds, this.appliedBrandIds, partialName).subscribe({
      next: (data) => {
        this.rebuildAvailableFiltersFromFacets(data);
        this.cdr.detectChanges();
      },
      error: (error) => {
        console.error('[App] Failed to load facets:', error);
        this.cdr.detectChanges();
      }
    });
  }

  toggleCategory(categoryId: number, checked: boolean) {
    this.selectedCategoryIds = checked
      ? [...new Set([...this.selectedCategoryIds, categoryId])]
      : this.selectedCategoryIds.filter(id => id !== categoryId);

    this.appliedCategoryIds = [...this.selectedCategoryIds];
    this.resetResults();
    this.loadData();
  }

  toggleBrand(brandId: number, checked: boolean) {
    this.selectedBrandIds = checked
      ? [...new Set([...this.selectedBrandIds, brandId])]
      : this.selectedBrandIds.filter(id => id !== brandId);

    this.appliedBrandIds = [...this.selectedBrandIds];
    this.resetResults();
    this.loadData();
  }

  get visibleCategories(): Category[] {
    return this.showAllCategories ? this.categories : this.categories.slice(0, 10);
  }

  get visibleBrands(): Brand[] {
    return this.showAllBrands ? this.brands : this.brands.slice(0, 10);
  }

  getBrandCount(brandId: number): number {
    return this.facetCounts.find(f => f.facetType === 'brand' && f.facetId === brandId)?.productCount ?? 0;
  }

  getCategoryCount(categoryId: number): number {
    return this.facetCounts.find(f => f.facetType === 'category' && f.facetId === categoryId)?.productCount ?? 0;
  }

  toggleCategories() {
    this.showAllCategories = !this.showAllCategories;
  }

  toggleBrands() {
    this.showAllBrands = !this.showAllBrands;
  }
}
