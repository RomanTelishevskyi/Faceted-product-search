import {Injectable, inject} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';


export interface Facet {

  facetType: string;
  facetId: number;
  facetName: string;
  productCount: number;
}

@Injectable({
  providedIn: 'root',
})
export class FacetService {
  private http = inject(HttpClient);
  private apiUrl = 'https://faceted-product-search.onrender.com/facets';

  getFacets(categoryIds: number[] = [], brandIds: number[] = [], partialName: string = '') {
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

    return this.http.get<Facet[]>(this.apiUrl, { params });
  }
}
