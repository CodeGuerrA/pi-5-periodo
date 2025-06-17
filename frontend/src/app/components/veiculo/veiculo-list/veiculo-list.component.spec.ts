import { ComponentFixture, TestBed } from '@angular/core/testing';

import { VeiculoListComponent } from './veiculo-list.component';

describe('VeiculoListComponent', () => {
  let component: VeiculoListComponent;
  let fixture: ComponentFixture<VeiculoListComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [VeiculoListComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(VeiculoListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
