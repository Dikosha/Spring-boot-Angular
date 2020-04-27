import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../_services/token-storage.service';
import {ScriptLoadingService} from '../_services/script-loading.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'app-about',
  templateUrl: './about.component.html',
  styleUrls: ['./about.component.css']
})
export class AboutComponent implements OnInit {

  masters = [];
  // masters: Array<{master_id: number; master_name: string}> = [];

  constructor(private tokenStorage: TokenStorageService, private http: HttpClient, private scriptLoadingService: ScriptLoadingService) { }

  ngOnInit(): void {
    this.scriptLoadingService.loadScript();
    const url = 'http://localhost:8080/master/getAll';
    this.http.post <any>(url,  {},
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' })
        })
        .subscribe(
            data => {
              console.log(data);
              this.masters = data.RESULT;
              console.log(this.masters);
            }
        );
  }
}
