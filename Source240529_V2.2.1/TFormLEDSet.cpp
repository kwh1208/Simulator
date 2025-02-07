//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TFormLEDSet.h"
#include "TFormMain.h"
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "TntStdCtrls"
#pragma link "te_controls"
#pragma link "TntExtCtrls"
#pragma link "TntButtons"
#pragma link "TntComCtrls"
#pragma resource "*.dfm"
TFormLEDSet *FormLEDSet;
//---------------------------------------------------------------------------
__fastcall TFormLEDSet::TFormLEDSet(TComponent* Owner)
        : TForm(Owner)
{
        this->Caption = FormMain->LangFile.FormLEDSetCaption;
        pnCaption->Caption = " " + this->Caption;
        //laDispType->Caption = FormMain->LangFile.BtnLEDSetting;
        laDispType->Caption = FormMain->LangFile.FormLEDSetLabel2;
        Label6->Caption = FormMain->LangFile.FormLEDSetLabel1;
        BSave->Caption       = FormMain->LangFile.MessageButton05;
        DataSend->Caption     = FormMain->LangFile.BtnSend;
        BitBtnOk->Caption = FormMain->LangFile.MessageButton10;
        DataSendAuto->Caption = FormMain->LangFile.MessageButton22;
        laDetail->Caption = FormMain->LangFile.FormLEDSetLabel3;
        BtnWrite->Caption = FormMain->LangFile.MessageButton17;
        BtnRead->Caption = FormMain->LangFile.MessageButton15;
        this->Color = FormMain->MainColor;
        pnMain->Color = FormMain->MainColor;
        pnMainDivide->Color = FormMain->MainDivideColor;
        pnDivide->Color = FormMain->MainDivideColor;
        pnCaption->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnBorderIcon->Color = FormMain->MainDivideColor;//FormMain->ButtonColor;
        pnCaption->Font->Color = FormMain->CaptionTextColor;
        pnCaption->Font->Name = FormMain->MainFont;
        pnCaption->Font->Size = 10;//FormMain->MainFontSize;
        laDispType->Font->Color = FormMain->TextColor;
        laDispType->Font->Name = FormMain->MainFont;
        laDispType->Font->Size = FormMain->MainFontSize;
        lbDispType->Font->Name = FormMain->MainFont;
        lbDispType->Font->Size = FormMain->MainFontSize;
        laNotice->Font->Color = FormMain->TextColor;
        laNotice->Font->Name = FormMain->MainFont;
        laNotice->Font->Size = FormMain->MainFontSize;
        eNotice->Font->Name = FormMain->MainFont;
        eNotice->Font->Size = FormMain->MainFontSize;
        TntLabel2->Font->Color = FormMain->TextColor;
        TntLabel2->Font->Name = FormMain->MainFont;
        TntLabel2->Font->Size = FormMain->MainFontSize;
        cbDispColor->Font->Name = FormMain->MainFont;
        cbDispColor->Font->Size = FormMain->MainFontSize;
        TntLabel1->Font->Color = FormMain->TextColor;
        TntLabel1->Font->Name = FormMain->MainFont;
        TntLabel1->Font->Size = FormMain->MainFontSize;
        cbScanOder->Font->Name = FormMain->MainFont;
        cbScanOder->Font->Size = FormMain->MainFontSize;
        Label6->Font->Color = FormMain->TextColor;
        Label6->Font->Name = FormMain->MainFont;
        Label6->Font->Size = FormMain->MainFontSize;
        eUserNotice->Font->Name = FormMain->MainFont;
        eUserNotice->Font->Size = FormMain->MainFontSize;
        BSave->Font->Color = FormMain->TextBGColor;
        BSave->Font->Name = FormMain->MainFont;
        BSave->Font->Size = FormMain->MainFontSize;
        pnBSave->Color = FormMain->ButtonColor;
        DataSend->Font->Color = FormMain->TextBGColor;
        DataSend->Font->Name = FormMain->MainFont;
        DataSend->Font->Size = FormMain->MainFontSize;
        pnDataSend->Color = FormMain->ButtonColor;
        DataSendAuto->Font->Color = FormMain->TextBGColor;
        DataSendAuto->Font->Name = FormMain->MainFont;
        DataSendAuto->Font->Size = FormMain->MainFontSize;
        pnDataSendAuto->Color = FormMain->ButtonColor;
        BitBtnOk->Font->Color = FormMain->TextBGColor;
        BitBtnOk->Font->Name = FormMain->MainFont;
        BitBtnOk->Font->Size = FormMain->MainFontSize;
        pnBitBtnOk->Color = FormMain->ButtonColor;
        laDetail->Color = FormMain->MainColor;
        laDetail->Font->Color = FormMain->TextColor;
        laDetail->Font->Name = FormMain->MainFont;
        laDetail->Font->Size = FormMain->MainFontSize;
        BtnWrite->Font->Color = FormMain->TextBGColor;
        BtnWrite->Font->Name = FormMain->MainFont;
        BtnWrite->Font->Size = FormMain->MainFontSize;
        pnBtnWrite->Color = FormMain->ButtonColor;
        BtnRead->Font->Color = FormMain->TextBGColor;
        BtnRead->Font->Name = FormMain->MainFont;
        BtnRead->Font->Size = FormMain->MainFontSize;
        pnBtnRead->Color = FormMain->ButtonColor;
        TntLabel6->Font->Color = FormMain->TextColor;
        TntLabel6->Font->Name = FormMain->MainFont;
        TntLabel6->Font->Size = FormMain->MainFontSize;
        TntLabel5->Font->Color = FormMain->TextColor;
        TntLabel5->Font->Name = FormMain->MainFont;
        TntLabel5->Font->Size = FormMain->MainFontSize;
        TntLabel3->Font->Color = FormMain->TextColor;
        TntLabel3->Font->Name = FormMain->MainFont;
        TntLabel3->Font->Size = FormMain->MainFontSize;
        TntLabel7->Font->Color = FormMain->TextColor;
        TntLabel7->Font->Name = FormMain->MainFont;
        TntLabel7->Font->Size = FormMain->MainFontSize;
        TntLabel4->Font->Color = FormMain->TextColor;
        TntLabel4->Font->Name = FormMain->MainFont;
        TntLabel4->Font->Size = FormMain->MainFontSize;
        gbDetail->Color = FormMain->MainRectangleColor;
        pnDetail->Color = FormMain->MainColor;
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::FormCreate(TObject *Sender)
{
        AnsiString asDspType3[] =
            {"16D-P16D1S11","16D-P16D1S10-1","16D-P16D1S21-1","08D-P32D1S22-9<1","08D-P16D2S10-1^9",
             "08D-P16D2S11-1^9","08D-P16D2S31-9^1","08D-P16D2S23-1^2","08D-P32D1S31","08D-HUB75-D2-R","08D-P64D1S31","08D-P64D1S91-11",
             "04D-P32D2S31-2<1^4<3","04D-P32D2S41","04D-P32D2S51","04D-P16D4S21-1^5^9^13","01D-P64D4S21-4^8^12^16",
             "01D-P128D2S31"};
        AnsiString asDspTypeF[] =
           {"32D-P16D1S11","16D-P16D1S10-1","16D-P16D1S11","16D-P16D1S21","16D-P16D1S31","16D-P16D1S41","08D-P16D2S10-1^9","08D-P16D2S11-1^9","08D-P16D2S21","08D-P16D2S23-1^2",//10
            "08D-P16D2S31-9^1","08D-P16D2S81","08D-P32D1S11","08D-P32D1S12","08D-P32D1S22-9<1","08D-P32D1S31","08D-P32D1S32","08D-P64D1S21","08D-P64D1S31","08D-P64D1S41-11","08D-P64D1S61","08D-P64D1S91-11","08D-P128D1S42","08D-P128D1S51",//24
            "04D-P16D4S11-1^5^9^13","04D-P16D4S21-1^5^9^13","04D-P32D2S11","04D-P32D2S21","04D-P32D2S31-2<1^4<3","04D-P32D2S41","04D-P32D2S51","04D-P32D2S61","04D-P32D2S71","04D-P64D1S11","04D-D2-SA1-A4E8A8E8A4","04D-D2-SA2-E8A8E8A8",//36
            "02D-P128D2S21","02D-P64D2S21","02D-P64D2S31","02D-P64D2S41","02D-P256D2S41","01D-P64D4S21-4^8^12^16","01D-P128D2S21","01D-P128D2S31"};
        int tempItemCnt = 0;
        lbDispType->Items->Clear();
        //if(FormMain->Setup.ProgramType == e2BPP)
        //{
        //    for(int  i = 0; i < 18; i++)
        //        lbDispType->Items->Add(asDspType3[i]);
        //}
        //else
        //{
            for(int  i = 0; i < 44; i++)
            {
                //if(FormMain->Setup.ProgramType == e24BPP)
                //{
                //    if(i == 2 || i == 9 || i == 10 || i == 11 || i == 13 || i == 14 || i == 15 || i == 20 || i == 22 || i == 23 || i == 24 || i == 26 || i == 29)
                //        continue;
                //}
                lbDispType->Items->Add(asDspTypeF[i]);
            }
        //}
        lbDispType->ItemIndex = FormMain->DisplayType;
        if(lbDispType->ItemIndex < 0)
            lbDispType->ItemIndex = 0;
        lbDispTypeClick(this);
        if(FormMain->ProgramType != e2BPP)
            cbDispColor->ItemIndex = FormMain->ColorOder;
        else
        {
            TntLabel2->Visible = false;
            cbDispColor->Visible = false;
            cbDispColor->ItemIndex = 0;
        }
        //if(lbDispType->ItemIndex == 13)
            cbScanOder->ItemIndex = FormMain->ScanOder;
        //else
        //    cbScanOder->ItemIndex = 0;
        UpDownDetailDelayBefore->Position = FormMain->DetailDelayBefore;
        UpDownDetailDelayAfter->Position = FormMain->DetailDelayAfter;
        UpDownDelay->Position = FormMain->SignalAutoDelayTime;
        LONG style = GetWindowLong(eDelay->Handle, GWL_STYLE) | ES_RIGHT;
        SetWindowLong(eDelay->Handle, GWL_STYLE, style);
        SetWindowLong(eDetailDelayBefore->Handle, GWL_STYLE, style);
        SetWindowLong(eDetailDelayAfter->Handle, GWL_STYLE, style);
        AnsiString asLEDModuleSetFileName = FormMain->BasePath + IncludeTrailingPathDelimiter(FormMain->DestSysPath) + "LEDModuleNoticeData.dat";
        ZeroMemory(&LEDSetUserNotice, sizeof(LEDSetUserNotice));
        ZeroMemory(LEDSetColorOder, sizeof(LEDSetColorOder));
        ZeroMemory(LEDSetScanOder, sizeof(LEDSetScanOder));
        TFileStream *LEDModuleSetDataFile = NULL;
        eUserNotice->Text = "";
        if(FileExists(asLEDModuleSetFileName))
        {
            try{
                LEDModuleSetDataFile = new TFileStream(asLEDModuleSetFileName, fmOpenRead | fmShareCompat);
                LEDModuleSetDataFile->Read(&LEDSetUserNotice, sizeof(LEDSetUserNotice));
                if(FormMain->ProgramType == e2BPP)
                    asLEDModuleSetFileName = AnsiString((char *)LEDSetUserNotice[0][lbDispType->ItemIndex]).SubString(1, sizeof(LEDSetUserNotice[0][0]));
                else
                    asLEDModuleSetFileName = AnsiString((char *)LEDSetUserNotice[1][lbDispType->ItemIndex]).SubString(1, sizeof(LEDSetUserNotice[0][0]));
                eUserNotice->Text = asLEDModuleSetFileName;
                LEDModuleSetDataFile->Seek(sizeof(LEDSetUserNotice), soFromBeginning);
                LEDModuleSetDataFile->Read(LEDSetColorOder, sizeof(LEDSetColorOder));
                LEDModuleSetDataFile->Seek(sizeof(LEDSetUserNotice) + sizeof(LEDSetColorOder), soFromBeginning);
                LEDModuleSetDataFile->Read(LEDSetScanOder, sizeof(LEDSetScanOder));
            }catch(...){
            }
            if(LEDModuleSetDataFile)
            {
                delete LEDModuleSetDataFile;
                LEDModuleSetDataFile = NULL;
            }
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::lbDispTypeClick(TObject *Sender)
{
        lbDispType->Invalidate();
        eNotice->Text = "";
        cbScanOder->Items->Clear();
        cbScanOder->Enabled = false;
        cbScanOder->Items->Add("138 IC");
        switch(lbDispType->ItemIndex)
        {
            case 0:
            {
                eNotice->Text = eNotice->Text + "Full Color LED Module\r\nP2.5-64*64-32S-V5";"P2.5-64*64-32S-V5";
            }
            break;
            case 1:
            {
                eNotice->Text = eNotice->Text + "1/16 Duty : 3Color LED Module\r\n                Vissem : VL096TR-16S,\r\n                VS128T110-0";
            }
            break;
            case 2:
            {
                eNotice->Text = eNotice->Text + "1/16 Duty : Full Color LED Module\r\n      16 duty module in more than 2 rows,\r\n      HUB75: PH4-TCE,\r\n      PH3RGB1.1(64x32)-16S-S2020-DQ,\r\n      P7.62-16S-HX,Davit P3(KSL-K3-D-16S),\r\n      P4-2121-32*32-V2";
            }
            break;
            case 3:
            {
                eNotice->Text = eNotice->Text + "1/16 Duty : Full Color LED Module\r\n      16 duty module only in one row,\r\n      DABIT P3 64X16-1/16SCAN";
            }
            break;
            case 4:
            {
                eNotice->Text = eNotice->Text + "1/16 Duty : Full Color LED Module\r\n      16 duty module in more than 4 rows,\r\n      P3-1415-16S-64x64-S31";
            }
            break;
            case 5:
            {
                eNotice->Text = eNotice->Text + "1/16 Duty : Full Color LED Module\r\n      16 duty module in more than 4 rows,\r\n      GLSE P3-OUT-744H-2307-2A-842-A2-64x64-16S";
            }
            break;
            case 6:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : 3Color LED Module\r\n                   Vissem: VL128TR-08S,\r\n                   Vissem:VS192T110-8,\r\n                   VL200T210-8, VS128T110-8,";
            }
            break;
            case 7:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                   VS128T110-8,VS096T110-8,\r\n                   VS064T110-8,VS048T110-8,\r\n                   HUB75:PH6-1/8-1.7,P6FH8-06,\r\n                   PH7.62-8-V1.0,PH10-1/8,\r\n                   PH4-1/8-1.4,2P1300A,\r\n                   P6(3528)-8S-3216-A1";
            }
            break;
            case 8:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                CM80-D8FN-A\r\n                (CORUN FullColor Lamp Type 1/8D)";
            }
            break;
            case 9:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : 3Color LED Module\r\n                Gapo, Seoul, Leddis, Adtronic,\r\n                Comtel";
            }
            break;
            case 10:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                  HUB75:P6(3528)-8S-3216-A1,\r\n                  P6(3528)-8S-3216-A1";
            }
            break;
            case 11:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                GP08D";
            }
            break;
            case 12:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                KSL-32X64-8S-1921-V.1";
            }
            break;
            case 13:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                HRL-P5-8S-LED2727-64X32-V3.1";
            }
            break;
            case 14:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                HUB75: P8-3528-8S-V1";
            }
            break;
            case 15:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                HUB75: P6-32X32-8S-V2.0,\r\n                PH7.62-32X16-A";
            }
            break;
            case 16:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                S6(3535)-8S-3232-A6";
            }
            break;
            case 17:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                HUB75:LR-P6-32X32-8S-V2.0,\r\n                P5-32x32-8S-A,\r\n                DH180721-P4-0018K-500(595IC),\r\n                L980 8S 2525-V3.1(595IC:\r\n                SP 구형 미세먼지 전광판)";
                cbScanOder->Items->Add("595 IC");
                cbScanOder->Items->Add("SUM2017TD IC");
                cbScanOder->Enabled = true;
            }
            break;
            case 18:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                HUB75:PH7.62-32*16-A";
            }
            break;
            case 19:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                S6(3535)-8S-3232-A5";
            }
            break;
            case 20:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                128/P4/192 1/8D";
            }
            break;
            case 21:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                P6(3535)32x32-8S-V1.6,\r\n                P6-32x32-8S-V2.0,\r\n                HLQ-T4C2020H84";
            }
            break;
            case 22:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                P4(2121)-32x64-8s-15A";
            }
            break;
            case 23:
            {
                eNotice->Text = eNotice->Text + "1/8 Duty : Full Color LED Module\r\n                P4-64x32-2525-8S-JJ-V1.2";
            }
            break;
            case 24:
            {
                eNotice->Text = eNotice->Text + "1/4 Duty : Full Color LED Module\r\n                Gapo, Seoul";
            }
            break;
            case 25:
            {
                eNotice->Text = eNotice->Text + "1/4 Duty : 3Color LED Module\r\n                Leddis, Adtronic, Comtel";
            }
            break;
            case 26:
            {
                eNotice->Text = eNotice->Text + "1/4 Duty : Full Color LED Module\r\n                CM80-D4FN-A\r\n                (CORUN FullColor Lamp Type 1/4D)";
            }
            break;
            case 27:
            {
                eNotice->Text = eNotice->Text + "1/4 Duty : Full Color LED Module\r\n                HUB75: PH8-1/4-1.2,\r\n                L8-BKSL1709,\r\n                CM80-D43-200CN";
            }
            break;
            case 28:
            {
                eNotice->Text = eNotice->Text + "1/4 Duty : 3Color LED Module\r\n                Gapo, Seoul";
            }
            break;
            case 29:
            {
                eNotice->Text = eNotice->Text + "1/4 Duty : Full Color LED Module\r\n                HUB75: PH7.62-1/4-1.4";
            }
            break;
            case 30:
            {
                eNotice->Text = eNotice->Text + "1/4 Duty : Full Color LED Module\r\n                PH12-1/4-V1.3";
            }
            break;
            case 31:
            {
                eNotice->Text = eNotice->Text + "1/4 Duty : Full Color LED Module\r\n                HUB75: PH6-1/4-1.2,2PH15729R,\r\n                PH8-3535-4S-V1.0,\r\n                P8-32*16 REV1.6,4S-75-13A-W,\r\n                L800-32X16-4S-V3.0,\r\n                Leddis P8-32X16-4S\r\n                P10-3535-4S-SHL1.0";
                cbScanOder->Items->Add("L800");
                cbScanOder->Items->Add("No IC");
                cbScanOder->Enabled = true;
            }
            break;
            case 32:
            {
                eNotice->Text = eNotice->Text + "1/4 Duty : Full Color LED Module\r\n                P10-4S-3535-3216-V2.3";
            }
            break;
            case 33:
            {
                eNotice->Text = eNotice->Text + "1/4 Duty : Full Color LED Module\r\n                PH6-32x32-4SD27-V1";
            }
            break;
            case 34:
            {
                eNotice->Text = eNotice->Text + "1/4 Duty : Full Color LED Module\r\n                P10-4S-3535-3216-JA1";
            }
            break;
            case 35:
            {
                eNotice->Text = eNotice->Text + "1/4 Duty : Full Color LED Module\r\n   P8-3535-4S-V6.0,\r\n   P8-1_4_3535_PR4538_HSD_32x16_NRES";
            }
            break;
            case 36:
            {
                eNotice->Text = eNotice->Text + "1/2 Duty : Full Color LED Module\r\n                 HUB75:P10(3535)-16x32-2S-14A";
            }
            break;
            case 37:
            {
                eNotice->Text = eNotice->Text + "1/2 Duty : Full Color LED Module\r\n       PH10-3535-S2-RGB-A1(Gapo 1/2D P10),\r\n       P10(3535)-16x32-2S-14A";
            }
            break;
            case 38:
            {
                eNotice->Text = eNotice->Text + "1/2 Duty : Full Color LED Module\r\n                 PH12.5-16X16-2SD35";
            }
            break;
            case 39:
            {
                eNotice->Text = eNotice->Text + "1/2 Duty : Full Color LED Module\r\n                 GMSFC2-200x200(가포2D)";
            }
            break;
            case 40:
            {
                eNotice->Text = eNotice->Text + "1/2 Duty : Full Color LED Module\r\n                 P10_GAPO_NEW_2S";
            }
            break;
            case 41:
            {
                eNotice->Text = eNotice->Text + "Static : 3Color LED Module\r\n              Vissem VL240T110-1,\r\n              VL300T110-1, VL320T210-1\r\nStatic : Full Color LED Module\r\n              Vissem VL160F111-1,\r\n              VL200F111-1, VL300F111-1";
            }
            break;
            case 42:
            {
                eNotice->Text = eNotice->Text + "Static : Full Color LED Module\r\n         HUB75: OF16R3A2-1616-V1.0JC(75)";
            }
            break;
            case 43:
            {
                eNotice->Text = eNotice->Text + "Static : 3Color LED Module\r\n                 LEDDIS HUB75";
            }
            break;
        }
        cbScanOder->ItemIndex = 0;
        AnsiString eData = "";
        /*if(FormMain->ProgramType == e2BPP)
        {
            eData = AnsiString((char *)LEDSetUserNotice[0][lbDispType->ItemIndex]).SubString(1, sizeof(LEDSetUserNotice[0][0]));
            cbDispColor->ItemIndex = LEDSetColorOder[0][lbDispType->ItemIndex];
        }
        else
        {*/
            eData = AnsiString((char *)LEDSetUserNotice[1][lbDispType->ItemIndex]).SubString(1, sizeof(LEDSetUserNotice[0][0]));
            cbDispColor->ItemIndex = LEDSetColorOder[1][lbDispType->ItemIndex];
        //}
        cbScanOder->ItemIndex = LEDSetScanOder[1][lbDispType->ItemIndex];
        eUserNotice->Text = (WideString)eData;
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::lbDispTypeDrawItem(TWinControl *Control,
      int Index, TRect &Rect, TOwnerDrawState State)
{
        int     Offset = 2;
        TCanvas *pCanvas = ((TTntListBox *)Control)->Canvas;

        pCanvas->FillRect(Rect);
        if(Index == lbDispType->ItemIndex)
        {
            pCanvas->Font->Color = clRed;
            pCanvas->Font->Style = TFontStyles() << fsBold;
        }
        else
        {
            AnsiString eData = "";
            //if(FormMain->ProgramType == e2BPP)
            //    eData = AnsiString((char *)LEDSetUserNotice[0][Index]).SubString(1, sizeof(LEDSetUserNotice[0][0]));
            //else
                eData = AnsiString((char *)LEDSetUserNotice[1][Index]).SubString(1, sizeof(LEDSetUserNotice[0][0]));
            if(eData != "" && eData != " ")
                pCanvas->Font->Color = clBlue;
            else
                pCanvas->Font->Color = clBlack;
            if(Index == 1 || Index == 6 || Index == 9 || Index == 25 || Index == 28 || Index == 41 || Index == 43)
                pCanvas->Font->Color = clFuchsia;
            pCanvas->Font->Style = TFontStyles() >> fsBold;
        }
        pCanvas->TextOut(Rect.Left + Offset, Rect.Top, ((TTntListBox *)Control)->Items->Strings[Index]);
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::DataSendClick(TObject *Sender)
{
        if(Sender ==  DataSendAuto)
        {
            if(DataSendAuto->Caption == FormMain->LangFile.MessageButton14)
            {
                DataSendAuto->Caption = FormMain->LangFile.MessageButton22;
                FormMain->bStopSend = true;
                DataSend->Enabled = true;
                DataSendAuto->Enabled = true;
                BtnWrite->Enabled = true;
                BtnRead->Enabled = true;
                this->BringToFront();
                return;
            }
            else
                DataSendAuto->Caption = FormMain->LangFile.MessageButton14;
        }
        int Addr = 0;
        AnsiString asData = "";
        AnsiString TmpMsg = "";
        try{
            Addr = FormMain->Controler1;
        }catch(...){
            return;
        }
        DataSend->Enabled = false;
        //DataSendAuto->Enabled = false;
        BtnWrite->Enabled = false;
        BtnRead->Enabled = false;
        FormMain->bStopSend = false;
        BYTE Data[13];
        ZeroMemory(Data, sizeof(Data));
        int DataSize = 6;
        int iTotCnt = 1;
        int iDelay = 0;
        int iDTIndex = lbDispType->ItemIndex;
        if(Sender == DataSendAuto)
        {
            FormMain->SignalAutoDelayTime = StrToIntDef(eDelay->Text, 1);
            iDelay = FormMain->SignalAutoDelayTime * 1000;
            iTotCnt = 48;
            if(lbDispType->ItemIndex > 17 && lbDispType->ItemIndex <= 31)
                iDTIndex = lbDispType->ItemIndex + 2;
            else if(lbDispType->ItemIndex > 31)
                iDTIndex = lbDispType->ItemIndex + 4;
            else
                iDTIndex = lbDispType->ItemIndex;
        }
        else
            iTotCnt = lbDispType->ItemIndex + 1;
        int iSOIndex = cbScanOder->ItemIndex;
        int iDCIndex = cbDispColor->ItemIndex;
        TmpMsg = "Setting LED Module Display Type";
        for(int i = iDTIndex; i < iTotCnt; i++)
        {
            if(FormMain->bStopSend)
                break;
            if(Sender == DataSendAuto)
            {
                iSOIndex = 0;
                iDCIndex = 0;
                if(i > 19 && i < 34)
                    iDTIndex = i - 2;
                else if(i >= 34)
                    iDTIndex = i - 4;
                else
                    iDTIndex = i;
                if(i >= 17 && i <= 19)
                {
                    iDTIndex = 17;
                    iSOIndex = i - iDTIndex;
                }
                if(i >= 33 && i <= 35)
                {
                    iDTIndex = 31;
                    iSOIndex = i - iDTIndex - 2;
                }
                lbDispType->ItemIndex = iDTIndex;
                lbDispTypeClick(this);
                cbScanOder->ItemIndex = iSOIndex;
                //lbDispType->Refresh();
            }
            if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, false))
            {
                bool bConnect = false;
                memcpy(Data, FormMain->SetLEDModuleValue(iDTIndex, iDCIndex, iSOIndex), DataSize);
                if(FormMain->PorotocolType == 1)
                {
                    AnsiString asModuleData = "![" + FormMain->IntTo485Address(Addr) + "052";
                    if(Data[1] == 32)
                        asModuleData = asModuleData + 'B';
                    else if(Data[1] == 16)
                        asModuleData = asModuleData + 'A';
                    else
                        asModuleData = asModuleData + IntToStr(Data[1]);
                    //asModuleData = asModuleData + Format("%02d", ARRAYOFCONST((Data[2])));
                    //asModuleData = asModuleData + IntToStr(Data[3]) + IntToStr(Data[4]) + IntToStr(Data[5]) + "!]";
                    asModuleData = asModuleData + IntToStr(Data[2]);
                    union{
                        BYTE cbSize;
                        struct{
                            unsigned bit0 : 4;
                            unsigned bit1 : 4;
                        }Bit;
                    }uSize;
                    uSize.cbSize = Data[3];
                    char cNum = 0x00;
                    cNum = uSize.Bit.bit1 + 0x30;
                    asModuleData = asModuleData + cNum;
                    cNum = uSize.Bit.bit0 + 0x30;
                    asModuleData = asModuleData + cNum;
                    asModuleData = asModuleData + IntToStr(Data[4]) + IntToStr(Data[5]) + "!]";
                    DataSize = asModuleData.Length();
                    memcpy(Data, asModuleData.c_str(), DataSize);
                    bConnect = FormMain->ModuleASCIIData(FormMain->ComKind, Addr, Data, DataSize, true);
                }
                else
                    bConnect = FormMain->ModuleData(FormMain->ComKind, Addr, Data, DataSize, 0, (OPCode)DTS, true);
                if(!bConnect)
                    FormMain->AddLog(TmpMsg + " [FAIL]" + FormMain->asLogMessage, clRed);
                else
                {
                    FormMain->AddLog(TmpMsg + " [OK]" + FormMain->asLogMessage, clBlack);
                    FormMain->SchBmpFile = false;
                    FormMain->asLogMessage = "";
                }
                FormMain->ComClose(FormMain->ComKind);
            }
            if(Sender == DataSendAuto && !FormMain->bStopSend)
                FormMain->DelayCount(iDelay);
        }
        if(Sender == DataSendAuto)
        {
            if(DataSendAuto->Caption == FormMain->LangFile.MessageButton14)
                DataSendAuto->Caption = FormMain->LangFile.MessageButton22;
        }
        BSave->Click();
        DataSend->Enabled = true;
        DataSendAuto->Enabled = true;
        BtnWrite->Enabled = true;
        BtnRead->Enabled = true;
        this->BringToFront();
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::BitBtnOkClick(TObject *Sender)
{
        FormMain->DisplayType = lbDispType->ItemIndex;
        FormMain->ColorOder = cbDispColor->ItemIndex;
        FormMain->ScanOder = cbScanOder->ItemIndex;
        FormMain->DetailDelayBefore = UpDownDetailDelayBefore->Position;
        FormMain->DetailDelayAfter = UpDownDetailDelayAfter->Position;
        FormMain->SignalAutoDelayTime = UpDownDelay->Position;
        BSave->Click();
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::BSaveClick(TObject *Sender)
{
        AnsiString eData = eUserNotice->Text;
        int iLen = eData.Length();
        if(iLen > sizeof(LEDSetUserNotice[0][0]))
            iLen = sizeof(LEDSetUserNotice[0][0]);
        if(FormMain->ProgramType == e2BPP)
        {
            ZeroMemory(&LEDSetUserNotice[0][lbDispType->ItemIndex], sizeof(LEDSetUserNotice[0][0]));
            memcpy(&LEDSetUserNotice[0][lbDispType->ItemIndex], eData.c_str(), iLen);
            LEDSetColorOder[0][lbDispType->ItemIndex] = cbDispColor->ItemIndex;
        }
        else
        {
            ZeroMemory(&LEDSetUserNotice[1][lbDispType->ItemIndex], sizeof(LEDSetUserNotice[0][0]));
            memcpy(&LEDSetUserNotice[1][lbDispType->ItemIndex], eData.c_str(), iLen);
            LEDSetColorOder[1][lbDispType->ItemIndex] = cbDispColor->ItemIndex;
        }
        LEDSetScanOder[1][lbDispType->ItemIndex] = cbScanOder->ItemIndex;
        AnsiString asLEDModuleSetFileName = FormMain->BasePath + IncludeTrailingPathDelimiter(FormMain->DestSysPath) + "LEDModuleNoticeData.dat";
        TFileStream *LEDModuleSetDataFile = NULL;
        try{
            LEDModuleSetDataFile = new TFileStream(asLEDModuleSetFileName, fmCreate | fmShareCompat);
            LEDModuleSetDataFile->Write(&LEDSetUserNotice, sizeof(LEDSetUserNotice));
            LEDModuleSetDataFile->Write(LEDSetColorOder, sizeof(LEDSetColorOder));
            LEDModuleSetDataFile->Write(LEDSetScanOder, sizeof(LEDSetScanOder));
        }catch(...){
        }
        if(LEDModuleSetDataFile)
        {
            delete LEDModuleSetDataFile;
            LEDModuleSetDataFile = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::lbDispTypeDblClick(TObject *Sender)
{
        DataSend->Click();
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::lbDispTypeKeyUp(TObject *Sender, WORD &Key,
      TShiftState Shift)
{
        if(Key == VK_RETURN)
        {
            DataSend->Click();
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::BitBtnCancelClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::btnHideClick(TObject *Sender)
{
        this->WindowState = wsMinimized;
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::btnMaxClick(TObject *Sender)
{
        if(this->WindowState == wsMaximized)
            this->WindowState = wsNormal;
        else
            this->WindowState = wsMaximized;
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::btnExitClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::pnCaptionMouseDown(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
        if(Button == mbLeft)
        {
            OldXPos = X;
            OldYPos = Y;
            bCaptionMove = true;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::pnCaptionMouseUp(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
        if(Button == mbLeft)
        {
            bCaptionMove = false;
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::pnCaptionMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
        if(bCaptionMove)
        {
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormLEDSet::BtnWriteClick(TObject *Sender)
{
        int Addr = 0;
        AnsiString asData = "";
        AnsiString TmpMsg = "";
        try{
            Addr = FormMain->Controler1;
        }catch(...){
            return;
        }
        this->Enabled = false;
        DataSend->Enabled = false;
        DataSendAuto->Enabled = false;
        BtnWrite->Enabled = false;
        BtnRead->Enabled = false;
        FormMain->bStopSend = false;
        int DataSize = 0;
        AnsiString asMsg = "";
        AnsiString asBluetoothData = "";
        BYTE Data[13];
        DataSize = sizeof(Data);
        ZeroMemory(Data, sizeof(Data));
        OPCode tempOPCode;
        int tempType = FormMain->PorotocolType;
        FormMain->PorotocolType = 1;
        if(Sender == BtnRead)
        {
            if(FormMain->PorotocolType)
            {
                asBluetoothData = "![" + FormMain->IntTo485Address(Addr) + "0B50!]";
                DataSize = asBluetoothData.Length();
            }
            else
            {
                Data[0] = 0x01;
                DataSize = 2;
                tempOPCode = DSC;
            }
            asMsg = "Detail Delay Read";
        }
        else
        {
            if(FormMain->PorotocolType)
            {
                asBluetoothData = "![" + FormMain->IntTo485Address(Addr) + "0B4";
                asMsg = eDetailDelayBefore->Text.SubString(1, 2);
                DataSize = asMsg.Length();
                if(DataSize < 2)
                {
                    for(int i = DataSize; i < 2; i++)
                        asMsg = asMsg.Insert(" ", 1);
                }
                asBluetoothData = asBluetoothData + asMsg + " ";
                asMsg = eDetailDelayAfter->Text.SubString(1, 2);
                DataSize = asMsg.Length();
                if(DataSize < 2)
                {
                    for(int i = DataSize; i < 2; i++)
                        asMsg = asMsg.Insert(" ", 1);
                }
                asBluetoothData = asBluetoothData + asMsg + "!]";
                DataSize = asBluetoothData.Length();
            }
            else
            {
                Data[1] = UpDownDetailDelayBefore->Position;
                Data[2] = UpDownDetailDelayAfter->Position;
                DataSize = 3;
                tempOPCode = DSC;
            }
            asMsg = "Detail Delay Write";
        }
        if(FormMain->ComPortSet(FormMain->ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, FormMain->IPAddress, FormMain->IPPort, false))
        {
            if(FormMain->PorotocolType)
            {
                memcpy(Data, asBluetoothData.c_str(), DataSize);
                if(!FormMain->ModuleASCIIData(FormMain->ComKind, Addr, Data, DataSize, FormMain->bMsg))
                    FormMain->AddLog(asMsg + " [FAIL]" + FormMain->asLogMessage, clRed);
                else
                {
                    FormMain->AddLog(asMsg + " [OK]" + FormMain->asLogMessage, clBlack);
                    FormMain->SchBmpFile = false;
                    FormMain->asLogMessage = "";
                }
           }
           else
           {
                if(!FormMain->ModuleData(FormMain->ComKind, Addr, Data, DataSize, 0, (OPCode)tempOPCode, FormMain->bMsg))
                    FormMain->AddLog(TmpMsg + " [FAIL]" + FormMain->asLogMessage, clRed);
                else
                {
                    FormMain->AddLog(TmpMsg + " [OK]" + FormMain->asLogMessage, clBlack);
                    FormMain->SchBmpFile = false;
                    FormMain->asLogMessage = "";
                }
            }
            FormMain->ComClose(FormMain->ComKind);
        }
        FormMain->PorotocolType = tempType;
        BSave->Click();
        this->Enabled = true;
        DataSend->Enabled = true;
        DataSendAuto->Enabled = true;
        BtnWrite->Enabled = true;
        BtnRead->Enabled = true;
        this->BringToFront();
}
//---------------------------------------------------------------------------


