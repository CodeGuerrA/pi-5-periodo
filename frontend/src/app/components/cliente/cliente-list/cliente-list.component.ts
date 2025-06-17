import { Component, OnInit } from '@angular/core';
import { Cliente } from '../../../model/cliente.module';
import { ClienteService } from '../../../service/cliente.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-cliente-list',
  standalone: false,
  templateUrl: './cliente-list.component.html',
  styleUrls: ['./cliente-list.component.css']
})
export class ClienteListComponent implements OnInit {
  clientes: Cliente[] = [];

  constructor(private clienteService: ClienteService) {}

  ngOnInit(): void {
    this.carregarClientes();
  }

  carregarClientes(): void {
    this.clienteService.getClientes()
      .subscribe(data => this.clientes = data);
  }

  excluir(id: number): void {
    if (!confirm('Deseja realmente excluir este cliente?')) {
      return;
    }
    this.clienteService.deleteCliente(id)
      .subscribe({
        next: () => {
          // Remove da lista sem recarregar toda a página
          this.clientes = this.clientes.filter(cliente => cliente.id !== id);
        },
        error: err => {
          console.error('Erro ao excluir cliente', err);
          alert('Não foi possível excluir o cliente.');
        }
      });
  }
}
