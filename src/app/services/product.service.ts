import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';

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
  private apiUrl = 'https://faceted-product-search.onrender.com/products/search';

  getProducts(
    categoryIds: number[] = [],
    brandIds: number[] = [],
    partialName: string = '',
    offset = 0
  ) {
    let params = new HttpParams();

    if (partialName.trim()) {
      params = params.set('partialName', partialName.trim());
    }

    for (const id of categoryIds) {
      params = params.append('categoryIds', id.toString());
    }

    for (const id of brandIds) {
      params = params.append('brandIds', id.toString());
    }

    params = params.set('offset', offset.toString());

    return this.http.get<ProductDTO[]>(this.apiUrl, { params });
  }
}
