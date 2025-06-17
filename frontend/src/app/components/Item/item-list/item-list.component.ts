import { Component, OnInit } from '@angular/core';
import { ItemPecaOS, ItemPecaOsService } from '../../../service/item.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-item-peca-list',
  standalone: false,
  templateUrl: './item-list.component.html',
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {
  itens: ItemPecaOS[] = [];

  constructor(
    private itemService: ItemPecaOsService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.carregarItens();
  }

  carregarItens(): void {
    this.itemService.listarTodos().subscribe(data => {
      this.itens = data;
    });
  }

  excluir(id: number): void {
    if (confirm('Tem certeza que deseja excluir este item?')) {
      this.itemService.deletar(id).subscribe(() => {
        this.carregarItens();
      });
    }
  }
}
