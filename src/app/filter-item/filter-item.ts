import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-filter-item',
  imports: [],
  templateUrl: './filter-item.html',
  styleUrl: './filter-item.css',
})
export class FilterItem {
  @Input() name: string = '';
  @Input() count = 0;
  @Input() checked = false;
  @Output() checkedChange = new EventEmitter<boolean>();

  onChange(event: Event) {
    const input = event.target as HTMLInputElement;
    this.checkedChange.emit(input.checked);
  }
}
