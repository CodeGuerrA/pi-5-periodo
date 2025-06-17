import { Component, OnInit } from '@angular/core';
import { VeiculoService } from '../../../service/veiculo.service';
import { Veiculo } from '../../../model/veiculo.module';

@Component({
  selector: 'app-veiculo-list',
  standalone: false,
  templateUrl: './veiculo-list.component.html',
  styleUrl: './veiculo-list.component.css'
})
export class VeiculoListComponent implements OnInit {
  veiculos: Veiculo[] = [];

  constructor(private veiculoService: VeiculoService) {}

  ngOnInit(): void {
    this.veiculoService.getVeiculos().subscribe(data => this.veiculos = data);
  }

  excluir(id: number): void {
  this.veiculoService.deleteVeiculo(id).subscribe(() => {
    this.veiculos = this.veiculos.filter(v => v.id !== id);
  });
  }
}
