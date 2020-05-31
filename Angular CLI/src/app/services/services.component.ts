import { Component, OnInit } from '@angular/core';
import {ScriptLoadingService} from '../_services/script-loading.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {TokenStorageService} from '../_services/token-storage.service';


@Component({
  selector: 'app-services',
  templateUrl: './services.component.html',
  styleUrls: ['./services.component.css']
})
export class ServicesComponent implements OnInit {

  services = [];
  constructor(private tokenStorage: TokenStorageService, private http: HttpClient, private scriptLoadingService: ScriptLoadingService) { }

  ngOnInit(): void {
    this.scriptLoadingService.loadScript();
    const url = 'http://localhost:8080/service/getAll';
    this.http.post <any>(url,  {},
        {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' })
        })
        .subscribe(
            data => {
              console.log(data);
              this.services = data.RESULT;
              console.log(this.services);
            }
        );
  }
}
