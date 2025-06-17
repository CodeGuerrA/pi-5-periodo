import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ServicoService } from '../../../service/servico.service';
import { Servico } from '../../../model/servico.module';

@Component({
  selector: 'app-servico-form',
  standalone: false,
  templateUrl: './servico-form.component.html',
  styleUrls: ['./servico-form.component.css']
})
export class ServicoFormComponent implements OnInit {
  servicoForm!: FormGroup;
  servicoId?: number;
  isEditMode = false;

  constructor(
    private fb: FormBuilder,
    private servicoService: ServicoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.servicoForm = this.fb.group({
      nome: ['', Validators.required],
      valor: [null, [Validators.required, Validators.min(0)]]
    });

    this.servicoId = Number(this.route.snapshot.paramMap.get('id'));
    this.isEditMode = !!this.servicoId;

    if (this.isEditMode) {
      this.servicoService.getServicoById(this.servicoId).subscribe((servico) => {
        this.servicoForm.patchValue(servico);
      });
    }
  }

  onSubmit(): void {
    if (this.servicoForm.invalid) return;

    const servico: Servico = this.servicoForm.value;

    if (this.isEditMode && this.servicoId) {
      this.servicoService.updateServico(this.servicoId, servico).subscribe(() => {
        this.router.navigate(['/servicos/editar/:id']);
      });
    } else {
      this.servicoService.createServico(servico).subscribe(() => {
        this.router.navigate(['/servicos/novo']);
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/servicos']);
  }
}
