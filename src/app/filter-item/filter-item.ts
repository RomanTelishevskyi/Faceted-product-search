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
    console.log('[FilterItem] checkbox changed');
    console.log('[FilterItem] name:', this.name);
    console.log('[FilterItem] checked:', input.checked);

    this.checkedChange.emit(input.checked);
  }
}
