import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PecaListComponent } from './peca-list.component';

describe('PecaListComponent', () => {
  let component: PecaListComponent;
  let fixture: ComponentFixture<PecaListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PecaListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PecaListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
