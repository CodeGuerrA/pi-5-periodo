import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Peca } from '../model/pecas.module';

@Injectable({
  providedIn: 'root'
})
export class PecaService {
  private apiUrl = 'http://localhost:8080/pecas';

  constructor(private http: HttpClient) {}

  getPecas(): Observable<Peca[]> {
    return this.http.get<Peca[]>(this.apiUrl);
  }

  getPecaById(id: number): Observable<Peca> {
    return this.http.get<Peca>(`${this.apiUrl}/${id}`);
  }

  createPeca(peca: Peca): Observable<Peca> {
    return this.http.post<Peca>(this.apiUrl, peca);
  }

  updatePeca(id: number, peca: Peca): Observable<Peca> {
    return this.http.put<Peca>(`${this.apiUrl}/${id}`, peca);
  }

  deletePeca(id: number): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`);
  }
}
