import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { TradeService } from '../../trade.service'; 
import { Trade } from 'src/app/models/trade';
import * as pdfMake from 'pdfmake/build/pdfmake';
import * as pdfFonts from 'pdfmake/build/vfs_fonts';
import { saveAs } from 'file-saver';
import { VerifyService } from 'src/app/verify.service';
(pdfMake as any).vfs = pdfFonts.pdfMake.vfs;



@Component({
  selector: 'app-report',
  templateUrl: './report.component.html',
  styleUrls: ['./report.component.css']
})
export class ReportComponent implements OnInit {
  currentReport: string = '';
  buyReportData: Trade[] = [];
  sellReportData: Trade[] = [];
  cashBalance: number = 10000000;
  buySellChartData: any[] = [];
  holdingsByCategoryData: any[] = [];
  instrumentBuyDescriptions:string[]=[];
  instrumentSellDescription:string[]=[];
  currentClientId:any;

  constructor(private tradeService: TradeService,
    private verifyService:VerifyService) {
    this.buyTable = undefined;
    this.sellTable = undefined;
  }

  ngOnInit(): void {
    // Load initial data or perform any necessary setup here
    // this.showBuyReport();
    // this.showSellReport();

  }

   async showBuyReport() {
    this.verifyService.getCurrentClientId().subscribe({
      next: (x) => {
        this.currentClientId = x;
        console.log("ID IN COMPONENT => ",this.currentClientId);
      },
    });
    this.tradeService.getCurrentUserBuyReport(this.currentClientId).subscribe((data) => {
      this.buyReportData = data;
    });

    for(let i=0;i<this.buyReportData.length;i++)
    {
      this.tradeService.getInstrumentDescription(this.buyReportData[i].instrumentId).subscribe((data) => {
        this.instrumentBuyDescriptions.push(data);
      });
    }
    console.log(this.buyReportData);
    this.currentReport='buy';

  }

  async showSellReport() {
    this.tradeService.getCurrentUserSellReport(this.currentClientId).subscribe((data) => {
      this.sellReportData = data;
    });

    for(let i=0;i<this.sellReportData.length;i++)
    {
      this.tradeService.getInstrumentDescription(this.sellReportData[i].instrumentId).subscribe((data) => {
        this.instrumentSellDescription.push(data);
      });
    }
    this.currentReport = 'sell';
  }
 
  @ViewChild('buyTable', { static: false }) buyTable: ElementRef | undefined;
  @ViewChild('sellTable', { static: false }) sellTable: ElementRef | undefined;

  generateBuyReportCsv() {
    const headers = ['Instrument ID', 'Instrument Description', 'Trade ID', 'Quantity', 'Execution Price'];
    const buyReportData = [...this.buyReportData]; // Replace with your actual Buy Report data
  
    // Create the CSV content, including headers
    let csvContent = headers.join(',') + '\n';
  
    // Add data rows to the CSV content
    for(let i=0;i<buyReportData.length;i++)
    {
      const rowData = [
        buyReportData[i].instrumentId,
        this.instrumentBuyDescriptions[i].replace(/,/, ''),
        buyReportData[i].tradeId,
        buyReportData[i].quantity,
        buyReportData[i].executionPrice
      ];
      csvContent += rowData.join(',') + '\n';
    }
    
  
    // Create a Blob from the CSV content
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8' });
  
    // Trigger the download
    saveAs(blob, 'buy_report.xlsx');
  }
  
  generateSellReportCsv()
  {
    const headers = ['Instrument ID', 'Instrument Description', 'Trade ID', 'Quantity', 'Execution Price'];
    const sellReportData = [...this.sellReportData]; // Replace with your actual Buy Report data
  
    // Create the CSV content, including headers
    let csvContent = headers.join(',') + '\n';
  
    // Add data rows to the CSV content
    for(let i=0;i<sellReportData.length;i++)
    {
      const rowData = [
        sellReportData[i].instrumentId,
        this.instrumentSellDescription[i].replace(/,/, ''),
        sellReportData[i].tradeId,
        sellReportData[i].quantity,
        sellReportData[i].executionPrice
      ];
      csvContent += rowData.join(',') + '\n';
    }
  
    const blob = new Blob([csvContent], { type: 'text/csv;charset=utf-8' });
  
    saveAs(blob, 'sell_report.xlsx');
  }
  
}
