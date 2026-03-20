import { Injectable, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface Brand {
  id: number;
  name: string;
}

@Injectable({
  providedIn: 'root',
})
export class BrandService {
  private http = inject(HttpClient);
  private apiUrl = 'http://localhost:8080/brand/';

  getBrands() {
    return this.http.get<Brand[]>(this.apiUrl);
  }
}
