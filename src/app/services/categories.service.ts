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
  private apiUrl = 'http://localhost:8080/categories/';

  getCategories() {
    return this.http.get<Category[]>(this.apiUrl);
  }
}
