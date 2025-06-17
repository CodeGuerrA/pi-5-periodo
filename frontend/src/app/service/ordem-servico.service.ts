import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface OrdemServicoDTO {
  clienteId: number;
  veiculoId: number;
  itemPecaIds: number[];
  quantidadePecas: number[];
  servicoIds: number[];
  descricaoProblema: string;
  status: string;
}

export interface FinalizacaoOrdemDTO {
  idOrdemServico: number;
  formaPagamento: string;
}

@Injectable({
  providedIn: 'root',
})
export class OrdemServicoService {
  private readonly API = 'http://localhost:8080/api/ordens-servico';

  constructor(private http: HttpClient) {}

  listarTodos(): Observable<any[]> {
    return this.http.get<any[]>(this.API);
  }

  buscarPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.API}/${id}`);
  }

  criar(ordem: OrdemServicoDTO): Observable<any> {
    return this.http.post<any>(this.API, ordem);
  }

  atualizar(id: number, ordem: OrdemServicoDTO): Observable<any> {
    return this.http.put<any>(`${this.API}/${id}`, ordem);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API}/${id}`);
  }

  finalizar(dto: FinalizacaoOrdemDTO): Observable<string> {
    return this.http.post(`${this.API}/${dto.idOrdemServico}/finalizar`, dto, { responseType: 'text' });
  }

  gerarRelatorioFinanceiro(id: number): Observable<Blob> {
    return this.http.get(`${this.API}/${id}/relatorio-financeiro`, {
      responseType: 'blob',
      headers: new HttpHeaders().set('Accept', 'application/pdf')
    });
  }
}
