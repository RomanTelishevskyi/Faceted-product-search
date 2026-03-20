import {Component, Input} from '@angular/core';

@Component({
  selector: 'app-filter-item',
  imports: [],
  templateUrl: './filter-item.html',
  styleUrl: './filter-item.css',
})
export class FilterItem {

  @Input() name: String = '';
}
