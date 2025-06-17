import { Component, OnInit } from '@angular/core';
import { OrdemServicoService } from '../../../service/ordem-servico.service';
import { OrdemServico } from '../../../model/ordem-servico.module';
import { Router } from '@angular/router';

@Component({
  selector: 'app-ordem-servico-list',
  standalone: false,
  templateUrl: './ordem-servico-list.component.html',
  styleUrls: ['./ordem-servico-list.component.css']
})
export class OrdemServicoListComponent implements OnInit {
  ordens: OrdemServico[] = [];

  constructor(
    private ordemServicoService: OrdemServicoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarOrdens();
  }

  carregarOrdens(): void {
    this.ordemServicoService.listarTodos().subscribe(data => {
      this.ordens = data;
    });
  }

  excluir(id: number): void {
    if (confirm('Deseja realmente excluir esta ordem de serviço?')) {
      this.ordemServicoService.deletar(id).subscribe(() => {
        this.carregarOrdens();
      });
    }
  }
}
