import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ItemPecaOsDTO, ItemPecaOsService } from '../../../service/item.service';
import { PecaService } from '../../../service/peca.service';

@Component({
  selector: 'app-item-peca-form',
  standalone: false,
  templateUrl: './item-form.component.html',
  styleUrls: ['./item-form.component.css']
})
export class ItemFormComponent implements OnInit {
  itemForm!: FormGroup;
  itemId?: number;
  isEditMode = false;
  pecas: any[] = [];

  constructor(
    private fb: FormBuilder,
    private route: ActivatedRoute,
    private router: Router,
    private itemService: ItemPecaOsService,
    private pecaService: PecaService
  ) {}

  ngOnInit(): void {
    this.itemForm = this.fb.group({
      pecaId: ['', Validators.required],
      quantidade: ['', [Validators.required, Validators.min(1)]]
    });

    this.pecaService.getPecas().subscribe((data) => {
      this.pecas = data;
    });

    this.itemId = Number(this.route.snapshot.paramMap.get('id'));
    this.isEditMode = !!this.itemId;

    if (this.isEditMode && this.itemId) {
      this.itemService.buscarPorId(this.itemId).subscribe((item) => {
        this.itemForm.patchValue({
          pecaId: item.peca?.id,
          quantidade: item.quantidade
        });
      });
    }
  }

  onSubmit(): void {
    if (this.itemForm.invalid) return;

    const dto: ItemPecaOsDTO = this.itemForm.value;

    if (this.isEditMode && this.itemId) {
      this.itemService.atualizar(this.itemId, dto).subscribe(() => {
        this.router.navigate(['/itens/editar/:id']);
      });
    } else {
      this.itemService.criar(dto).subscribe(() => {
        this.router.navigate(['/itens/novo']);
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/itens']);
  }
}
