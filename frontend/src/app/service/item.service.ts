import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

export interface ItemPecaOS {
  id: number;
  peca: any; // você pode tipar conforme o model de Peca se desejar
  quantidade: number;
}

export interface ItemPecaOsDTO {
  pecaId: number;
  quantidade: number;
}

@Injectable({
  providedIn: 'root'
})
export class ItemPecaOsService {
  private readonly API_URL = 'http://localhost:8080/api/itens-peca';

  constructor(private http: HttpClient) {}

  listarTodos(): Observable<ItemPecaOS[]> {
    return this.http.get<ItemPecaOS[]>(this.API_URL);
  }

  buscarPorId(id: number): Observable<ItemPecaOS> {
    return this.http.get<ItemPecaOS>(`${this.API_URL}/${id}`);
  }

  criar(dto: ItemPecaOsDTO): Observable<ItemPecaOS> {
    return this.http.post<ItemPecaOS>(this.API_URL, dto);
  }

  atualizar(id: number, dto: ItemPecaOsDTO): Observable<ItemPecaOS> {
    return this.http.put<ItemPecaOS>(`${this.API_URL}/${id}`, dto);
  }

  deletar(id: number): Observable<void> {
    return this.http.delete<void>(`${this.API_URL}/${id}`);
  }
}
