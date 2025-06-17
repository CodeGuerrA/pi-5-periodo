import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, FormArray } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { OrdemServicoService } from '../../../service/ordem-servico.service';
import { ClienteService } from '../../../service/cliente.service';
import { VeiculoService } from '../../../service/veiculo.service';
import { Cliente } from '../../../model/cliente.module';
import { Veiculo } from '../../../model/veiculo.module';
import { ItemPecaOsService } from '../../../service/item.service';
import { ServicoService } from '../../../service/servico.service';

@Component({
  selector: 'app-ordem-servico-form',
  standalone: false,
  templateUrl: './ordem-servico-form.component.html',
  styleUrls: ['./ordem-servico-form.component.css']
})
export class OrdemServicoFormComponent implements OnInit {
  ordemForm!: FormGroup;
  clientes: Cliente[] = [];
  veiculos: Veiculo[] = [];
  itensPeca: any[] = [];
  servicos: any[] = [];
  ordemId?: number;
  isEditMode = false;
  statusOptions = ['ABERTA', 'EM_ANDAMENTO', 'FINALIZADA'];

  constructor(
    private fb: FormBuilder,
    private ordemService: OrdemServicoService,
    private clienteService: ClienteService,
    private veiculoService: VeiculoService,
    private itemPecaService: ItemPecaOsService,
    private servicoService: ServicoService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.ordemForm = this.fb.group({
      clienteId: ['', Validators.required],
      veiculoId: ['', Validators.required],
      descricaoProblema: ['', Validators.required],
      status: ['', Validators.required],
      itemPecaIds: [[], Validators.required],
      quantidadePecas: this.fb.array([]),
      servicoIds: [[], Validators.required],
    });

    this.ordemId = Number(this.route.snapshot.paramMap.get('id'));
    this.isEditMode = !!this.ordemId;

    this.clienteService.getClientes().subscribe((clientes) => (this.clientes = clientes));
    this.veiculoService.getVeiculos().subscribe((veiculos) => (this.veiculos = veiculos));
    this.itemPecaService.listarTodos().subscribe((itens) => (this.itensPeca = itens));
    this.servicoService.getServicos().subscribe((servicos) => (this.servicos = servicos));

    if (this.isEditMode) {
      this.ordemService.buscarPorId(this.ordemId).subscribe((ordem) => {
        this.ordemForm.patchValue({
          clienteId: ordem.cliente?.id,
          veiculoId: ordem.veiculo?.id,
          descricaoProblema: ordem.descricaoProblema,
          status: ordem.status,
          itemPecaIds: ordem.itens.map((i: any) => i.id),
          servicoIds: ordem.servicos.map((s: any) => s.id),
        });
      });
    }
  }

  onSubmit(): void {
    if (this.ordemForm.invalid) return;

    const dto = this.ordemForm.value;

    if (this.isEditMode && this.ordemId) {
      this.ordemService.atualizar(this.ordemId, dto).subscribe(() => {
        this.router.navigate(['/ordem-servicos/editar/:id']);
      });
    } else {
      this.ordemService.criar(dto).subscribe(() => {
        this.router.navigate(['/ordem-servicos/novo']);
      });
    }
  }

  cancelar(): void {
    this.router.navigate(['/ordem-servicos']);
  }
}
