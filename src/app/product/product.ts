import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-product',
  imports: [],
  templateUrl: './product.html',
  styleUrl: './product.css',
})
export class Product {
  @Input() photoSrc = 'https://via.placeholder.com/72';
  @Input() photoAlt = 'Product photo';
  @Input() id = 'ID-001';
  @Input() name = 'Product name';
  @Input() brand = 'Brand name';
  @Input() categories: string[] = ['Category 1', 'Category 2'];
}
