import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { VeiculoService } from '../../../service/veiculo.service';
import { ClienteService } from '../../../service/cliente.service';
import { Veiculo } from '../../../model/veiculo.module';
import { Cliente } from '../../../model/cliente.module';

@Component({
  selector: 'app-veiculo-form',
  standalone: false,
  templateUrl: './veiculo-form.component.html',
  styleUrls: ['./veiculo-form.component.css']
})
export class VeiculoFormComponent implements OnInit {
  veiculoForm!: FormGroup;
  veiculoId?: number;
  isEditMode = false;
  clientes: Cliente[] = [];
  tipos: string[] = ['CARRO', 'MOTO', 'CAMINHAO']; // ajuste conforme seu enum no backend

  constructor(
    private fb: FormBuilder,
    private veiculoService: VeiculoService,
    private clienteService: ClienteService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.veiculoForm = this.fb.group({
      placa: ['', [Validators.required, Validators.pattern(/^[A-Z]{3}[0-9]{4}$/)]],
      modelo: ['', Validators.required],
      ano: ['', [Validators.required, Validators.pattern(/^\d{4}$/)]],
      cor: [''],
      tipo: ['', Validators.required],
      clienteId: ['', Validators.required]
    });

    this.veiculoId = Number(this.route.snapshot.paramMap.get('id'));
    this.isEditMode = !!this.veiculoId;

    this.clienteService.getClientes().subscribe((clientes) => {
      this.clientes = clientes;
    });

    if (this.isEditMode) {
      this.veiculoService.getVeiculoById(this.veiculoId).subscribe((veiculo) => {
        this.veiculoForm.patchValue(veiculo);
      });
    }
  }

  onSubmit(): void {
    if (this.veiculoForm.invalid) return;

    const veiculo: Veiculo = this.veiculoForm.value;

    if (this.isEditMode && this.veiculoId) {
      this.veiculoService.updateVeiculo(this.veiculoId, veiculo).subscribe(() => {
        this.router.navigate(['/veiculos/editar', this.veiculoId]);
      });
    } else {
      this.veiculoService.createVeiculo(veiculo).subscribe(() => {
        this.router.navigate(['/veiculos/novo']);
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/veiculos']);
  }
}
