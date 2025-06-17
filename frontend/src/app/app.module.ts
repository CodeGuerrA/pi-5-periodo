import { NgModule } from '@angular/core';
import { BrowserModule, provideClientHydration, withEventReplay } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { HttpClientModule } from '@angular/common/http';
import { AppComponent } from './app.component';
import { VeiculoListComponent } from './components/veiculo/veiculo-list/veiculo-list.component';
import { VeiculoFormComponent } from './components/veiculo/veiculo-form/veiculo-form.component';
import { MainNavComponent } from './components/main-nav/main-nav.component';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatButtonModule } from '@angular/material/button';
import { MatSidenavModule } from '@angular/material/sidenav';
import { MatIconModule } from '@angular/material/icon';
import { MatListModule } from '@angular/material/list';
import { ClienteListComponent } from './components/cliente/cliente-list/cliente-list.component';
import { ReactiveFormsModule } from '@angular/forms';
import { ClienteFormComponent } from './components/cliente/cliente-form/cliente-form.component';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { ServicoFormComponent } from './components/servico/servico-form/servico-form.component';
import { ServicoListComponent } from './components/servico/servico-list/servico-list.component';
import { PecaListComponent } from './components/peca/peca-list/peca-list.component';
import { PecaFormComponent } from './components/peca/peca-form/peca-form.component';
import { PagamentoListComponent } from './components/pagamento/pagamento-list/pagamento-list.component';
import { PagamentoFormComponent } from './components/pagamento/pagamento-form/pagamento-form.component';
import { ItemListComponent } from './components/Item/item-list/item-list.component';
import { ItemFormComponent } from './components/Item/item-form/item-form.component';
import { OrdemServicoFormComponent } from './components/ordem-servico/ordem-servico-form/ordem-servico-form.component';
import { OrdemServicoListComponent } from './components/ordem-servico/ordem-servico-list/ordem-servico-list.component';


@NgModule({
  declarations: [
  
    AppComponent,
    VeiculoListComponent,
    VeiculoFormComponent,
    MainNavComponent,
    ClienteListComponent,
    ClienteFormComponent,
    ServicoFormComponent,
    ServicoListComponent,
    PecaListComponent,
    PecaFormComponent,
    PagamentoListComponent,
    PagamentoFormComponent,
    ItemListComponent,
    ItemFormComponent,
    OrdemServicoFormComponent,
    OrdemServicoListComponent,
    
  ],
  imports: [
    MatSelectModule,
    MatInputModule,
    MatFormFieldModule,
    HttpClientModule,
    BrowserModule,
    AppRoutingModule,
    MatToolbarModule,
    MatButtonModule,
    MatSidenavModule,
    MatIconModule,
    MatListModule,
    ReactiveFormsModule
  ],
  providers: [
    provideClientHydration(withEventReplay())
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
