import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../_services/token-storage.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {ServiceViewModel} from '../admin/admin.component';

@Component({
  selector: 'app-reserve',
  templateUrl: './reserve.component.html',
  styleUrls: ['./reserve.component.css']
})
export class ReserveComponent implements OnInit {

  constructor(private tokenStorage: TokenStorageService, private http: HttpClient) { }
  private url = 'http://localhost:8080';
  masterList = [];
  serviceList = [];
  date;
  time;
  servicePrice = 0;

  model: ReserveViewModel = {
    id: 0,
    date: '',
    client_id: this.tokenStorage.getUser().id,
    master_id: 0,
    service_id: 0
  };


  ngOnInit(): void {
    // @ts-ignore
    $('.dateselect').datepicker({
      format: 'dd.mm.yyyy',
    });

    this.http.post <any>(this.url + '/master/getAll', {}, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    })
        .subscribe(
            data => {
              console.log(data);
              this.masterList = data.RESULT;
              console.log(this.masterList);
            }
        );

  }

  onMasterChange(): void {
    // console.log('123456');
    this.http.post <any>(this.url + '/admin/getAllServices', {}, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    })
        .subscribe(
            data => {
              // console.log(data);
              this.serviceList = data.RESULT;
              console.log(this.serviceList);
            }
        );
  }

  onServiceChange(): void {
    console.log('onservicechange');
    console.log(this.model);
    this.model.service_id = Number(this.model.service_id);
    this.model.master_id = Number(this.model.master_id);
    // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < this.serviceList.length; i++) {
      if (this.model.service_id === this.serviceList[i].service_id) {
        this.servicePrice = this.serviceList[i].service_price;
      }
    }
    console.log(this.servicePrice);
  }

  reserve(): void {
    // @ts-ignore
    this.date = document.getElementById('date_id').value;
    const dateArray = this.date.split('.');
    // @ts-ignore
    this.model.date =  dateArray[2] + '-' + dateArray[1] + '-' + dateArray[0] + 'T' + this.time;
    console.log(this.model);
    this.http.post <any>(this.url + '/record/addRecord', this.model, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    })
        .subscribe(
            data => {
              console.log(data);
              if (data.STATUS === 200) {
                location.reload();
              }
            }
        );
  }

}

export interface ReserveViewModel {
  id: number;
  date: string;
  client_id: number;
  master_id: number;
  service_id: number;
}

