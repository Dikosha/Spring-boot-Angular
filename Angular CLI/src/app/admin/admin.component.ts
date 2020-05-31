import { Component, OnInit } from '@angular/core';
import {TokenStorageService} from '../_services/token-storage.service';
import {HttpClient, HttpHeaders} from '@angular/common/http';

@Component({
  selector: 'app-admin',
  templateUrl: './admin.component.html',
  styleUrls: ['./admin.component.css']
})
export class AdminComponent implements OnInit {
  isAdmin = false;
  usersList;
  serviceList;
  masterList;
  masterServicesList;
  constructor(private tokenStorage: TokenStorageService, private http: HttpClient) { }

    model: ServiceViewModel = {
        id: 0,
        description: '',
        name: '',
        price: 0
    };

    masterModel: MasterViewModel = {
        id: 0,
        name: '',
        phone: '',
        services: []
    };


    ngOnInit(): void {
    console.log(this.tokenStorage.getUser());
    if (this.tokenStorage.getUser() != null){
      for (let i = 0; i < this.tokenStorage.getUser().roles.length; i++) {
        if (this.tokenStorage.getUser().roles[i] === 'ROLE_ADMIN') {
          this.isAdmin = true;
        }
      }
    }
    if(this.isAdmin == false){
      window.location.href = 'login';
    }
    let url = 'http://localhost:8080/admin/getAllUsers';
    this.http.post <any>(url, {
    }, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    })
        .subscribe(
            data => {
              // console.log(data);
              this.usersList = data.RESULT;
              for (let i = 0; i < this.usersList.length; i++) {
                this.usersList[i].num = i + 1;
              }
              // console.log(this.usersList);
            }
        );
    url = 'http://localhost:8080/admin/getAllServices';
    this.http.post <any>(url, {}, {
          headers: new HttpHeaders({ 'Content-Type': 'application/json' })
      })
          .subscribe(
              data => {
                  // console.log(data);
                  this.serviceList = data.RESULT;
                  for (let i = 0; i < this.serviceList.length; i++) {
                      this.serviceList[i].num = i + 1;
                  }
                  // console.log(this.serviceList);
              }
          );

    url = 'http://localhost:8080/master/getAll';
    this.http.post <any>(url, {}, {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    })
      .subscribe(
          data => {
              // console.log(data);
              this.masterList = data.RESULT;
              for (let i = 0; i < this.masterList.length; i++) {
                  this.masterList[i].num = i + 1;
              }
              console.log(this.masterList);
          }
      );
  }

  blockUser(id): void {
      // console.log(id);
      const url = 'http://localhost:8080/admin/blockUser';
      this.http.post <any>(url, id,
{
          headers: new HttpHeaders({ 'Content-Type': 'application/json' })
      })
          .subscribe(
              data => {
                  // console.log(data);
                  window.location.reload();
              }
          );
  }
  unblockUser(id): void {
      // console.log(id);
      const url = 'http://localhost:8080/admin/unblockUser';
      this.http.post <any>(url, id,
          {
              headers: new HttpHeaders({ 'Content-Type': 'application/json' })
          })
          .subscribe(
              data => {
                  // console.log(data);
                  window.location.reload();
              }
          );
  }

  emptyModel(): void {
      this.model.id = 0;
      this.model.name = '';
      this.model.description = '';
      this.model.price = 0;
  }
  emptyMasterModel(): void {
      this.masterModel.id = 0;
      this.masterModel.name = '';
      this.masterModel.phone = '';
      this.masterModel.services = [];
  }


    addService(): void {
      // console.log(this.model);
      this.http.post('http://localhost:8080/admin/addService', this.model).subscribe(
          res => {
              console.log(res);
              // @ts-ignore
              if (res.STATUS === 200) {
                  location.reload();
              } else {
                  alert('Not Added. Try again.');
              }
          },
          error => {
              alert('Something is wrong');
          }
      );
  }
  showEditService(id): void {
      // tslint:disable-next-line:prefer-for-of
    for (let i = 0; i < this.serviceList.length; i++) {
        if (this.serviceList[i].service_id === id) {
            this.model.id = id;
            this.model.name = this.serviceList[i].service_name;
            this.model.description = this.serviceList[i].service_description;
            this.model.price = this.serviceList[i].service_price;
        }
    }
  }
  deleteService(id): void {
      // console.log(id);
      this.http.post('http://localhost:8080/admin/deleteService', id).subscribe(
          res => {
              console.log(res);
              // @ts-ignore
              if (res.STATUS === 200) {
                  location.reload();
              } else {
                  alert('Not Deleted. Try again.');
              }
          },
          error => {
              alert('Something is wrong');
          }
      );
  }

  editService(): void {
    // console.log(this.model);
    this.http.post('http://localhost:8080/admin/editService', this.model).subscribe(
          res => {
              console.log(res);
              // @ts-ignore
              if (res.STATUS === 200) {
                  location.reload();
              } else {
                  alert('Not Edited. Try again.');
              }
          },
          error => {
              alert('Something is wrong');
          }
      );
  }
    deleteMaster(id): void {
        // console.log(id);
        this.http.post('http://localhost:8080/master/delete', id).subscribe(
            res => {
                console.log(res);
                // @ts-ignore
                if (res.STATUS === 200) {
                    location.reload();
                } else {
                    alert('Not Deleted. Try again.');
                }
            },
            error => {
                alert('Something is wrong');
            }
        );
    }
    editMaster(): void {
        // console.log(this.masterModel);
        const checkBoxes = document.querySelectorAll('.services_checkbox');
        // console.log(checkBoxes);
        // tslint:disable-next-line:prefer-for-of
        for (let i = 0; i < checkBoxes.length; i++) {
            // @ts-ignore
            if (checkBoxes[i].checked) {
                // @ts-ignore
                this.masterModel.services.push(checkBoxes[i].value);
            }
        }
        // console.log(masterServices);
        this.http.post('http://localhost:8080/master/editInformation', this.masterModel).subscribe(
            res => {
                console.log(res);
                // @ts-ignore
                if (res.STATUS === 200) {
                    location.reload();
                } else {
                    alert('Not Edited. Try again.');
                }
            },
            error => {
                alert('Something is wrong');
            }
        );
    }
    showEditMaster(id): void {
        // tslint:disable-next-line:prefer-for-of
        for (let i = 0; i < this.masterList.length; i++) {
            if (this.masterList[i].master_id === id) {
                this.masterModel.id = id;
                this.masterModel.name = this.masterList[i].master_name;
                this.masterModel.phone = this.masterList[i].master_phone;
            }
        }
        const url = 'http://localhost:8080/master/getServices';
        this.http.post <any>(url, id, {
            headers: new HttpHeaders({ 'Content-Type': 'application/json' })
        })
            .subscribe(
                data => {
                    // console.log(data);
                    this.masterServicesList = data.RESULT;
                    // tslint:disable-next-line:prefer-for-of
                    for (let i = 0; i < this.serviceList.length; i++) {
                        // tslint:disable-next-line:prefer-for-of
                        for (let j = 0; j < this.masterServicesList.length; j++) {
                            if (this.serviceList[i].service_id === this.masterServicesList[j].service_id) {
                                this.serviceList[i].checked = true;
                                break;
                            } else {
                                this.serviceList[i].checked = false;
                            }
                        }
                    }
                    console.log(this.serviceList);
                }
            );
    }
    addMaster(): void {
        // console.log(this.masterModel);
        const checkBoxes = document.querySelectorAll('.services_checkbox');
        // console.log(checkBoxes);
        // tslint:disable-next-line:prefer-for-of
        for (let i = 0; i < checkBoxes.length; i++) {
            // @ts-ignore
            if (checkBoxes[i].checked) {
                // @ts-ignore
                this.masterModel.services.push(checkBoxes[i].value);
            }
        }
        // console.log(masterServices);
        this.http.post('http://localhost:8080/master/addNew', this.masterModel).subscribe(
            res => {
                console.log(res);
                // @ts-ignore
                if (res.STATUS === 200) {
                    location.reload();
                } else {
                    alert('Not Added. Try again.');
                }
            },
            error => {
                alert('Something is wrong');
            }
        );
    }
}

export interface ServiceViewModel {
    id: number;
    description: string;
    name: string;
    price: number;
}

export interface MasterViewModel {
    id: number;
    name: string;
    phone: string;
    services: [];
}
