import {Injectable, inject} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';

export interface ProductDTO {
  id: number;
  productName: string;
  brands: string;
  imageFrontUrl: string;
  categories: string;
}

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/products/search';

  getProducts(categoryIds: number[] = [], brandIds: number[] = []) {
    console.log('[ProductService] getProducts() called');
    console.log('[ProductService] categoryIds:', categoryIds);
    console.log('[ProductService] brandIds:', brandIds);

    let params = new HttpParams();

    for (const id of categoryIds) {
      params = params.append('categoryIds', id.toString());
    }

    for (const id of brandIds) {
      params = params.append('brandIds', id.toString());
    }

    console.log('[ProductService] final request URL:', this.apiUrl);
    console.log('[ProductService] final params:', params.toString());

    return this.http.get<ProductDTO[]>(this.apiUrl, { params });
  }
}
