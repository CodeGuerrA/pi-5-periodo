import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { PecaService } from '../../../service/peca.service';
import { Peca } from '../../../model/pecas.module';

@Component({
  selector: 'app-peca-form',
  standalone: false,
  templateUrl: './peca-form.component.html',
  styleUrls: ['./peca-form.component.css']
})
export class PecaFormComponent implements OnInit {
  pecaForm!: FormGroup;
  pecaId?: number;
  isEditMode = false;

  constructor(
    private fb: FormBuilder,
    private pecaService: PecaService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.pecaForm = this.fb.group({
      codigo: ['', Validators.required],
      descricao: ['', Validators.required],
      precoUnitario: [0, [Validators.required, Validators.min(0)]],
      quantidadeEstoque: [0, [Validators.required, Validators.min(0)]]
    });

    this.pecaId = Number(this.route.snapshot.paramMap.get('id'));
    this.isEditMode = !!this.pecaId;

    if (this.isEditMode) {
      this.pecaService.getPecaById(this.pecaId).subscribe((peca) => {
        this.pecaForm.patchValue(peca);
      });
    }
  }

  onSubmit(): void {
    if (this.pecaForm.invalid) return;

    const peca: Peca = this.pecaForm.value;

    if (this.isEditMode && this.pecaId) {
      this.pecaService.updatePeca(this.pecaId, peca).subscribe(() => {
        this.router.navigate(['/pecas/editar/:id']);
      });
    } else {
      this.pecaService.createPeca(peca).subscribe(() => {
        this.router.navigate(['/pecas/novo']);
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/pecas']);
  }
}
