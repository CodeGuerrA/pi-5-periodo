import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PecaFormComponent } from './peca-form.component';

describe('PecaFormComponent', () => {
  let component: PecaFormComponent;
  let fixture: ComponentFixture<PecaFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [PecaFormComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PecaFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
