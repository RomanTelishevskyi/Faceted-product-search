import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Product } from './product/product';
import { CategoriesService, Category } from './services/categories.service';
import { FilterItem } from './filter-item/filter-item';
import { BrandService, Brand } from './services/brand.service';
import { ProductDTO, ProductService } from './services/product.service';
import { RouterOutlet } from '@angular/router';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-root',
  imports: [Product, FilterItem, RouterOutlet],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  categories: Category[] = [];
  brands: Brand[] = [];
  products: ProductDTO[] = [];

  selectedCategoryIds: number[] = [];
  selectedBrandIds: number[] = [];

  appliedCategoryIds: number[] = [];
  appliedBrandIds: number[] = [];

  showAllCategories = false;
  showAllBrands = false;
  loading = false;

  constructor(
    private categoriesService: CategoriesService,
    private brandService: BrandService,
    private productService: ProductService,
    private route: ActivatedRoute,
    private router: Router,
    private cdr: ChangeDetectorRef
  ) {}

  ngOnInit() {
    this.categoriesService.getCategories().subscribe({
      next: (data) => {
        this.categories = data;
        this.restoreFiltersFromUrl();
      },
      error: (error) => console.error('[App] Failed to load categories:', error)
    });

    this.brandService.getBrands().subscribe({
      next: (data) => {
        this.brands = data;
        this.restoreFiltersFromUrl();
      },
      error: (error) => console.error('[App] Failed to load brands:', error)
    });
  }

  private restoreFiltersFromUrl() {
    if (!this.categories.length || !this.brands.length) {
      return;
    }

    const categoryIdsParam = this.route.snapshot.queryParamMap.get('categoryIds') ?? '';
    const brandIdsParam = this.route.snapshot.queryParamMap.get('brandIds') ?? '';

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

    this.loadProducts();
  }

  private updateUrlFromFilters() {
    this.router.navigate([], {
      relativeTo: this.route,
      queryParams: {
        categoryIds: this.appliedCategoryIds.length ? this.appliedCategoryIds.join(',') : null,
        brandIds: this.appliedBrandIds.length ? this.appliedBrandIds.join(',') : null
      },
      queryParamsHandling: 'merge',
      replaceUrl: true
    });
  }

  loadProducts() {
    this.loading = true;
    this.products = [];

    this.updateUrlFromFilters();

    this.productService.getProducts(this.appliedCategoryIds, this.appliedBrandIds).subscribe({
  next: (data) => {

      this.products = data;
      this.loading = false;
      this.cdr.detectChanges();

  },
  error: (error) => {
    console.error('[App] Failed to load products:', error);
    this.loading = false;
  }
});
  }

  confirmChoices() {
    this.appliedCategoryIds = [...this.selectedCategoryIds];
    this.appliedBrandIds = [...this.selectedBrandIds];

    this.loadProducts();
  }

  toggleCategory(categoryId: number, checked: boolean) {
    this.selectedCategoryIds = checked
      ? [...new Set([...this.selectedCategoryIds, categoryId])]
      : this.selectedCategoryIds.filter(id => id !== categoryId);
  }

  toggleBrand(brandId: number, checked: boolean) {
    this.selectedBrandIds = checked
      ? [...new Set([...this.selectedBrandIds, brandId])]
      : this.selectedBrandIds.filter(id => id !== brandId);
  }

  get visibleCategories(): Category[] {
    return this.showAllCategories ? this.categories : this.categories.slice(0, 10);
  }

  get visibleBrands(): Brand[] {
    return this.showAllBrands ? this.brands : this.brands.slice(0, 10);
  }

  toggleCategories() {
    this.showAllCategories = !this.showAllCategories;
  }

  toggleBrands() {
    this.showAllBrands = !this.showAllBrands;
  }
}
