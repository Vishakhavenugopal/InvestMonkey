import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class InstrumentService {
  private apiUrl = 'http://localhost:3200/client/prices';

  constructor(private http: HttpClient) {}

  getInstruments(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
