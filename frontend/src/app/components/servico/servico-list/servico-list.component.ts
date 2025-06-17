import { Component, OnInit } from '@angular/core';
import { Servico } from '../../../model/servico.module';
import { ServicoService } from '../../../service/servico.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-servico-list',
  standalone: false,
  templateUrl: './servico-list.component.html',
  styleUrls: ['./servico-list.component.css']
})
export class ServicoListComponent implements OnInit {
  servico: Servico[] = [];

  constructor(private servicoService: ServicoService) {}

  ngOnInit(): void {
    this.carregarServicos();
  }

  carregarServicos(): void {
    this.servicoService.getServicos()
      .subscribe(data => this.servico = data);
  }

  excluir(id: number): void {
    if (!confirm('Deseja realmente excluir este serviço?')) {
      return;
    }
    this.servicoService.deleteServico(id)
      .subscribe({
        next: () => {
          // Remove da lista sem recarregar toda a página
          this.servico = this.servico.filter(servico => servico.id !== id);
        },
        error: err => {
          console.error('Erro ao excluir servico', err);
          alert('Não foi possível excluir o servico.');
        }
      });
  }
}
