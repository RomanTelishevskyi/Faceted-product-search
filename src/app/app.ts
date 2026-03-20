import {Component, OnInit} from '@angular/core';
import {RouterOutlet} from '@angular/router';
import {Product} from './product/product';
import {CategoriesService, Category} from './services/categories.service';
import {FilterItem} from './filter-item/filter-item';
import {BrandService} from './services/brand.service';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Product, FilterItem],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App implements OnInit {
  categories: String[] = [];
  brands: String[] = [];

  constructor(private categoriesService: CategoriesService, private brandService: BrandService) {
  }

  ngOnInit() {
    this.categoriesService.getCategories().subscribe({
      next: (data) => {
        this.categories = data.map(category => category.name);
      },
      error: (error) => {
        console.error('Failed to load categories:', error);
      }
    });
    this.brandService.getBrands().subscribe({
      next: (data) => {
        this.brands = data.map(brand => brand.name);
      },
      error: (error) => {
        console.error('Failed to load brands:', error);
      }
    });
  }
}
