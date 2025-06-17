import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { ClienteService } from '../../../service/cliente.service';
import { Cliente } from '../../../model/cliente.module';

@Component({
  selector: 'app-cliente-form',
  standalone: false,
  templateUrl: './cliente-form.component.html',
  styleUrls: ['./cliente-form.component.css']
})
export class ClienteFormComponent implements OnInit {
  clienteForm!: FormGroup;
  clienteId?: number;
  isEditMode = false;

  constructor(
    private fb: FormBuilder,
    private clienteService: ClienteService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.clienteForm = this.fb.group({
      nome: ['', Validators.required],
      endereco: ['', Validators.required],
      telefone: ['', Validators.required],
      documento: ['', Validators.required]
    });

    this.clienteId = Number(this.route.snapshot.paramMap.get('id'));
    this.isEditMode = !!this.clienteId;

    if (this.isEditMode) {
      this.clienteService.getClienteById(this.clienteId).subscribe((cliente) => {
        this.clienteForm.patchValue(cliente);
      });
    }
  }

  onSubmit(): void {
    if (this.clienteForm.invalid) return;

    const cliente: Cliente = this.clienteForm.value;

    if (this.isEditMode && this.clienteId) {
      this.clienteService.updateCliente(this.clienteId, cliente).subscribe(() => {
        this.router.navigate(['/clientes/editar/:id']);
      });
    } else {
      this.clienteService.createCliente(cliente).subscribe(() => {
        this.router.navigate(['/clientes/novo']);
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/clientes']);
  }
}
