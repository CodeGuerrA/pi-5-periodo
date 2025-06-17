import { Component, OnInit } from '@angular/core';
import { Peca } from '../../../model/pecas.module';
import { PecaService } from '../../../service/peca.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-peca-list',
  standalone: false,
  templateUrl: './peca-list.component.html',
  styleUrls: ['./peca-list.component.css']
})
export class PecaListComponent implements OnInit {
  pecas: Peca[] = [];

  constructor(private pecaService: PecaService) {}

  ngOnInit(): void {
    this.carregarPecas();
  }

  carregarPecas(): void {
    this.pecaService.getPecas()
      .subscribe(data => this.pecas = data);
  }

  excluir(id: number): void {
    if (!confirm('Deseja realmente excluir este peca?')) {
      return;
    }
    this.pecaService.deletePeca(id)
      .subscribe({
        next: () => {
          // Remove da lista sem recarregar toda a página
          this.pecas = this.pecas.filter(peca => peca.id !== id);
        },
        error: err => {
          console.error('Erro ao excluir peca', err);
          alert('Não foi possível excluir o peca.');
        }
      });
  }
}
