import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-product',
  imports: [],
  templateUrl: './product.html',
  styleUrl: './product.css',
})
export class Product {
  @Input() photoSrc = '';
  @Input() photoAlt = 'Product photo';
  @Input() id = 1;
  @Input() name = 'Product name';
  @Input() brands = 'Brand name';
  @Input() categories: string = 'Category 1, Category 2';
}
