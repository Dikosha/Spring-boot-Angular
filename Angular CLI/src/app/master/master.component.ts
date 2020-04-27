import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../_services/token-storage.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'app-master',
  templateUrl: './master.component.html',
  styleUrls: ['./master.component.css']
})

export class MasterComponent implements OnInit {
  isAuthorized = false;
  private masterName;
  private masterPhone;
  masterServices = [];
  masterComments: Array<{author_name: string, content: string}> = [];
  constructor(private tokenStorage: TokenStorageService, private http: HttpClient) {
  }

  ngOnInit(): void {
    console.log(this.tokenStorage.getUser());
    if (this.tokenStorage.getUser() != null) {
      this.isAuthorized = true;
    }
    const searchObject = window.location.search;
    const urlParams = new URLSearchParams(searchObject);
    const masterID = urlParams.get('id')
    const url = 'http://localhost:8080/master/profile';
    this.http.post <any>(url,  masterID,
    {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    })
        .subscribe(
            data => {
              console.log(data);
              this.masterName = data.RESULT.master.master_name;
              this.masterPhone = data.RESULT.master.master_phone;
              for (let i = 0; i < data.RESULT.services.length; i++) {
                this.masterServices[i] = data.RESULT.services[i].service_name;
              }
              console.log(this.masterServices);
              for (let i = 0; i < data.RESULT.master_comments.length; i++) {
                this.masterComments[i] = {author_name: '' , content: ''};
                this.masterComments[i].author_name = data.RESULT.master_comments[i].comment_author;
                this.masterComments[i].content = data.RESULT.master_comments[i].comment_content;
              }
            }
        );
  }
}

