import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface Category {
  id: number;
  name: string;
}

@Injectable({
  providedIn: 'root',
})
export class CategoriesService {
  private http = inject(HttpClient);
  private apiUrl = 'https://faceted-product-search.onrender.com/categories/';

  getCategories() {
    return this.http.get<Category[]>(this.apiUrl);
  }

  getCategoryByName(name: string) {
    return this.http.get<Category>(`${this.apiUrl}name/${encodeURIComponent(name)}`);
  }
}
