import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FilterItem } from './filter-item';

describe('FilterItem', () => {
  let component: FilterItem;
  let fixture: ComponentFixture<FilterItem>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FilterItem],
    }).compileComponents();

    fixture = TestBed.createComponent(FilterItem);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
