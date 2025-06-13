import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AtribuicaoServico } from '../model/atribuicao-servico.module';

@Injectable({ providedIn: 'root' })
export class AtribuicaoServicoService {
  private apiUrl = 'http://localhost:8080/atribuicoes';

  constructor(private http: HttpClient) {}

  getAtribuicoes(): Observable<AtribuicaoServico[]> {
    return this.http.get<AtribuicaoServico[]>(this.apiUrl);
  }

  createAtribuicao(atribuicao: AtribuicaoServico): Observable<AtribuicaoServico> {
    return this.http.post<AtribuicaoServico>(this.apiUrl, atribuicao);
  }

  deleteAtribuicao(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
