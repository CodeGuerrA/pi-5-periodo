import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { VeiculoListComponent } from './components/veiculo/veiculo-list/veiculo-list.component';
import { VeiculoFormComponent } from './components/veiculo/veiculo-form/veiculo-form.component';
import { ClienteListComponent } from './components/cliente/cliente-list/cliente-list.component';
import { ClienteFormComponent } from './components/cliente/cliente-form/cliente-form.component';
import { ServicoFormComponent } from './components/servico/servico-form/servico-form.component';
import { ServicoListComponent } from './components/servico/servico-list/servico-list.component';
import { PecaFormComponent } from './components/peca/peca-form/peca-form.component';
import { PecaListComponent } from './components/peca/peca-list/peca-list.component';
import { OrdemServicoListComponent } from './components/ordem-servico/ordem-servico-list/ordem-servico-list.component';
import { OrdemServicoFormComponent } from './components/ordem-servico/ordem-servico-form/ordem-servico-form.component';
import { ItemListComponent } from './components/Item/item-list/item-list.component';
import { ItemFormComponent } from './components/Item/item-form/item-form.component';

const routes: Routes = [

  { path: 'itens', component: ItemListComponent },
  { path: 'itens/novo', component: ItemFormComponent },
  { path: 'itens/editar/:id', component: ItemFormComponent },

  { path: 'ordem-servicos', component: OrdemServicoListComponent },
  { path: 'ordem-servicos/novo', component: OrdemServicoFormComponent },
  { path: 'ordem-servicos/editar/:id', component: OrdemServicoFormComponent },

  { path: 'servicos', component: ServicoListComponent },
  { path: 'servicos/novo', component: ServicoFormComponent },
  { path: 'servicos/editar/:id', component: ServicoFormComponent },

  { path: 'veiculos', component: VeiculoListComponent },
  { path: 'veiculos/novo', component: VeiculoFormComponent },
  { path: 'veiculos/editar/:id', component: VeiculoFormComponent },

  { path: 'clientes', component: ClienteListComponent },
  { path: 'clientes/novo', component: ClienteFormComponent },
  { path: 'clientes/editar/:id', component: ClienteFormComponent },

  { path: 'pecas', component: PecaListComponent },
  { path: 'pecas/novo', component: PecaFormComponent },
  { path: 'pecas/editar/:id', component: PecaFormComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
