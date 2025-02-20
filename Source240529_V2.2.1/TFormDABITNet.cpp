//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TFormDABITNet.h"
#include "TFormMain.h"
#include <IniFiles.hpp>
#include <windows.h>
#include <iphlpapi.h>
#include <registry.hpp>
#include <memory>
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "iphlpapi.lib"
#pragma link "te_controls"
#pragma link "TntButtons"
#pragma link "TntExtCtrls"
#pragma link "TntStdCtrls"
#pragma link "CPort"
#pragma link "MMTimer"
#pragma link "TntComCtrls"
#pragma resource "*.dfm"
TFormDABITNet *FormDABITNet;
//---------------------------------------------------------------------------

__fastcall TFormDABITNet::TFormDABITNet(TComponent* Owner)
        : TForm(Owner)
{
        for(int i = 0; i < 255; i++)
            MACIPData[i] = "";
        bSerial = false;
        MainColor = FormMain->MainColor;
        ButtonColor = FormMain->ButtonColor;
        TextBGColor = FormMain->TextBGColor;
        TextColor = FormMain->TextColor;
        MainDivideColor = FormMain->MainDivideColor;
        MainRectangleColor = FormMain->MainRectangleColor;
        CaptionPanelColor = FormMain->CaptionPanelColor;
        MainFont = FormMain->MainFont;
        MainFontSize = FormMain->MainFontSize;
        this->Color = MainColor;
        pnCaption->Color = MainDivideColor;
        pnBorderIcon->Color = MainDivideColor;
        pnCaption->Font->Color = FormMain->CaptionTextColor;
        pnCaption->Font->Name = MainFont;
        pnCaption->Font->Size = 10;//MainFontSize;
        pnMain->Color = MainColor;
        pnMainBar->Color = MainColor;//CaptionPanelColor;
        pnBoardList->Color = MainColor;//CaptionPanelColor;
        pnComKind->Color = MainColor;//CaptionPanelColor;
        pnICON->Color = MainColor;//CaptionPanelColor;
        tnNetwork->Color = MainColor;
        tnWiFi->Color = MainColor;
        tnOption->Color = MainColor;
        tnInfor->Color = MainColor;
        laLAN->Color = MainColor;
        pnLAN->Color = MainColor;
        rbEthernetSet->Color = MainColor;
        laComMode->Color = MainColor;
        pnComMode->Color = MainColor;
        rgStatic->Color = MainColor;
        rgDHCP->Color = MainColor;
        laOperationMode->Color = MainColor;
        pnOperationMode->Color = MainColor;
        laServer->Color = MainColor;
        pnServer->Color = MainColor;
        rbServer->Color = MainColor;
        rbClient->Color = MainColor;
        rbServerIP->Color = MainColor;
        rbServerName->Color = MainColor;
        cbWiFiSet->Color = MainColor;
        pnWiFiModeSetting->Color = MainColor;
        pnAPMode->Color = MainColor;
        rbSTA->Color = MainColor;
        rbAP->Color = MainColor;
        rbEth->Color = MainColor;
        laMainInterface->Color = MainColor;
        pnMainInterface->Color = MainColor;
        rbEthernet->Color = MainColor;
        rbBluetooth->Color = MainColor;
        rbWiFi->Color = MainColor;
        laSubInterface->Color = MainColor;
        pnSubInterface->Color = MainColor;
        rbTTL->Color = MainColor;
        rbRS232->Color = MainColor;
        laFirmwareInfor->Color = MainColor;
        pnFirmwareInfor->Color = MainColor;
        rbUDP->Color = MainColor;
        pnUDP->Color = MainColor;
        pnUDPMode->Color = MainColor;
        pnAdvanced->Color = MainColor;
        rbBoth->Color = MainColor;
        rbOnlyEth->Color = MainColor;
        rbOnlyWiFi->Color = MainColor;
        rbSerial->Color = MainColor;
        pnSerial->Color = MainColor;

        tsBoardList->Font->Color = TextColor;
        tsBoardList->Font->Name = MainFont;
        tsBoardList->Font->Size = MainFontSize;
        //laMACAddr->Font->Color = TextColor;
        laMACAddr->Font->Name = MainFont;
        laMACAddr->Font->Size = MainFontSize;
        tsNetwork->Font->Color = TextColor;
        tsNetwork->Font->Name = MainFont;
        tsNetwork->Font->Size = MainFontSize;
        tsWiFi->Font->Color = TextColor;
        tsWiFi->Font->Name = MainFont;
        tsWiFi->Font->Size = MainFontSize;
        tsOption->Font->Color = TextColor;
        tsOption->Font->Name = MainFont;
        tsOption->Font->Size = MainFontSize;
        tsInfor->Font->Color = TextColor;
        tsInfor->Font->Name = MainFont;
        tsInfor->Font->Size = MainFontSize;
        pnComKind->Font->Color = TextColor;
        pnComKind->Font->Name = MainFont;
        pnComKind->Font->Size = MainFontSize;
        //laHelp->Font->Name = MainFont;
        //laHelp->Font->Size = MainFontSize;
        TntLabel9->Font->Color = TextColor;
        TntLabel9->Font->Name = MainFont;
        TntLabel9->Font->Size = MainFontSize;
        laLAN->Font->Color = TextColor;
        laLAN->Font->Name = MainFont;
        laLAN->Font->Size = MainFontSize;
        rbEthernetSet->Font->Color = TextColor;
        rbEthernetSet->Font->Name = MainFont;
        rbEthernetSet->Font->Size = MainFontSize;
        laComMode->Font->Color = TextColor;
        laComMode->Font->Name = MainFont;
        laComMode->Font->Size = MainFontSize;
        rgStatic->Font->Color = TextColor;
        rgStatic->Font->Name = MainFont;
        rgStatic->Font->Size = MainFontSize;
        rgDHCP->Font->Color = TextColor;
        rgDHCP->Font->Name = MainFont;
        rgDHCP->Font->Size = MainFontSize;
        TntLabel3->Font->Color = TextColor;
        TntLabel3->Font->Name = MainFont;
        TntLabel3->Font->Size = MainFontSize;
        TntLabel4->Font->Color = TextColor;
        TntLabel4->Font->Name = MainFont;
        TntLabel4->Font->Size = MainFontSize;
        TntLabel5->Font->Color = TextColor;
        TntLabel5->Font->Name = MainFont;
        TntLabel5->Font->Size = MainFontSize;
        TntLabel7->Font->Color = TextColor;
        TntLabel7->Font->Name = MainFont;
        TntLabel7->Font->Size = MainFontSize;
        laOperationMode->Font->Color = TextColor;
        laOperationMode->Font->Name = MainFont;
        laOperationMode->Font->Size = MainFontSize;
        rbServer->Font->Color = TextColor;
        rbServer->Font->Name = MainFont;
        rbServer->Font->Size = MainFontSize;
        rbClient->Font->Color = TextColor;
        rbClient->Font->Name = MainFont;
        rbClient->Font->Size = MainFontSize;
        laServer->Font->Color = TextColor;
        laServer->Font->Name = MainFont;
        laServer->Font->Size = MainFontSize;
        rbServerIP->Font->Color = TextColor;
        rbServerIP->Font->Name = MainFont;
        rbServerIP->Font->Size = MainFontSize;
        rbServerName->Font->Color = TextColor;
        rbServerName->Font->Name = MainFont;
        rbServerName->Font->Size = MainFontSize;
        laServerIP->Font->Color = TextColor;
        laServerIP->Font->Name = MainFont;
        laServerIP->Font->Size = MainFontSize;
        laServerDDNS->Font->Color = TextColor;
        laServerDDNS->Font->Name = MainFont;
        laServerDDNS->Font->Size = MainFontSize;
        TntLabel8->Font->Color = TextColor;
        TntLabel8->Font->Name = MainFont;
        TntLabel8->Font->Size = MainFontSize;
        BtnConnect->Font->Color = TextBGColor;
        BtnConnect->Font->Name = MainFont;
        BtnConnect->Font->Size = MainFontSize;

        TntLabel11->Font->Color = TextColor;
        TntLabel11->Font->Name = MainFont;
        TntLabel11->Font->Size = MainFontSize;
        TntLabel12->Font->Color = TextColor;
        TntLabel12->Font->Name = MainFont;
        TntLabel12->Font->Size = MainFontSize;
        TntLabel6->Font->Color = TextColor;
        TntLabel6->Font->Name = MainFont;
        TntLabel6->Font->Size = MainFontSize;
        TntLabel14->Font->Color = TextColor;
        TntLabel14->Font->Name = MainFont;
        TntLabel14->Font->Size = MainFontSize;
        TntLabel13->Font->Color = TextColor;
        TntLabel13->Font->Name = MainFont;
        TntLabel13->Font->Size = MainFontSize;
        TntLabel15->Font->Color = TextColor;
        TntLabel15->Font->Name = MainFont;
        TntLabel15->Font->Size = MainFontSize;
        rbSTA->Font->Color = TextColor;
        rbSTA->Font->Name = MainFont;
        rbSTA->Font->Size = MainFontSize;
        rbAP->Font->Color = TextColor;
        rbAP->Font->Name = MainFont;
        rbAP->Font->Size = MainFontSize;
        rbEth->Font->Color = TextColor;
        rbEth->Font->Name = MainFont;
        rbEth->Font->Size = MainFontSize;
        cbWiFiSet->Font->Color = TextColor;
        cbWiFiSet->Font->Name = MainFont;
        cbWiFiSet->Font->Size = MainFontSize;
        laWiFiSet->Color = MainColor;
        laWiFiSet->Font->Color = TextColor;
        laWiFiSet->Font->Name = MainFont;
        laWiFiSet->Font->Size = MainFontSize;
        laMainInterface->Color = MainColor;
        laMainInterface->Font->Color = TextColor;
        laMainInterface->Font->Name = MainFont;
        laMainInterface->Font->Size = MainFontSize;
        rbEthernet->Font->Color = TextColor;
        rbEthernet->Font->Name = MainFont;
        rbEthernet->Font->Size = MainFontSize;
        rbBluetooth->Font->Color = TextColor;
        rbBluetooth->Font->Name = MainFont;
        rbBluetooth->Font->Size = MainFontSize;
        rbWiFi->Font->Color = TextColor;
        rbWiFi->Font->Name = MainFont;
        rbWiFi->Font->Size = MainFontSize;
        laSubInterface->Color = MainColor;
        laSubInterface->Font->Color = TextColor;
        laSubInterface->Font->Name = MainFont;
        laSubInterface->Font->Size = MainFontSize;
        rbTTL->Font->Color = TextColor;
        rbTTL->Font->Name = MainFont;
        rbTTL->Font->Size = MainFontSize;
        rbRS232->Font->Color = TextColor;
        rbRS232->Font->Name = MainFont;
        rbRS232->Font->Size = MainFontSize;
        laComMethod->Color = MainColor;
        laComMethod->Font->Color = TextColor;
        laComMethod->Font->Name = MainFont;
        laComMethod->Font->Size = MainFontSize;
        rbUDP->Font->Color = TextColor;
        rbUDP->Font->Name = MainFont;
        rbUDP->Font->Size = MainFontSize;
        laUDPType->Color = MainColor;
        laUDPType->Font->Color = TextColor;
        laUDPType->Font->Name = MainFont;
        laUDPType->Font->Size = MainFontSize;
        rbBoth->Font->Color = TextColor;
        rbBoth->Font->Name = MainFont;
        rbBoth->Font->Size = MainFontSize;
        rbOnlyEth->Font->Color = TextColor;
        rbOnlyEth->Font->Name = MainFont;
        rbOnlyEth->Font->Size = MainFontSize;
        rbOnlyWiFi->Font->Color = TextColor;
        rbOnlyWiFi->Font->Name = MainFont;
        rbOnlyWiFi->Font->Size = MainFontSize;
        Label15->Font->Color = TextColor;
        Label15->Font->Name = MainFont;
        Label15->Font->Size = MainFontSize;
        Label16->Font->Color = TextColor;
        Label16->Font->Name = MainFont;
        Label16->Font->Size = MainFontSize;
        Label1->Font->Color = TextColor;
        Label1->Font->Name = MainFont;
        Label1->Font->Size = MainFontSize;
        ComBox2->Font->Color = TextColor;
        ComBox2->Font->Name = MainFont;
        ComBox2->Font->Size = MainFontSize;
        Label2->Font->Color = TextColor;
        Label2->Font->Name = MainFont;
        Label2->Font->Size = MainFontSize;
        BaudBox2->Font->Color = TextColor;
        BaudBox2->Font->Name = MainFont;
        BaudBox2->Font->Size = MainFontSize;
        laSearchTarget->Color = MainColor;
        laSearchTarget->Font->Color = TextColor;
        laSearchTarget->Font->Name = MainFont;
        laSearchTarget->Font->Size = MainFontSize;
        cbSearchTarget->Font->Color = TextColor;
        cbSearchTarget->Font->Name = MainFont;
        cbSearchTarget->Font->Size = MainFontSize;
        laResponseType->Color = MainColor;
        laResponseType->Font->Color = TextColor;
        laResponseType->Font->Name = MainFont;
        laResponseType->Font->Size = MainFontSize;
        cbResponseType->Font->Color = TextColor;
        cbResponseType->Font->Name = MainFont;
        cbResponseType->Font->Size = MainFontSize;
        laAdvanced->Color = MainColor;
        laAdvanced->Font->Color = TextColor;
        laAdvanced->Font->Name = MainFont;
        laAdvanced->Font->Size = MainFontSize;
        laDB300Tab->Color = MainColor;
        laDB300Tab->Font->Color = TextColor;
        laDB300Tab->Font->Name = MainFont;
        laDB300Tab->Font->Size = MainFontSize;
        cbDB300Tab->Font->Color = TextColor;
        cbDB300Tab->Font->Name = MainFont;
        cbDB300Tab->Font->Size = MainFontSize;
        rbSerial->Font->Color = TextColor;
        rbSerial->Font->Name = MainFont;
        rbSerial->Font->Size = MainFontSize;
        BtnSystem->Font->Color = TextBGColor;
        BtnSystem->Font->Name = MainFont;
        BtnSystem->Font->Size = MainFontSize;
        BtnNetworkAdapter->Font->Color = TextBGColor;
        BtnNetworkAdapter->Font->Name = MainFont;
        BtnNetworkAdapter->Font->Size = MainFontSize;
        BtnDB300Info->Font->Color = TextBGColor;
        BtnDB300Info->Font->Name = MainFont;
        BtnDB300Info->Font->Size = MainFontSize;
        BtnSave->Font->Color = TextBGColor;
        BtnSave->Font->Name = MainFont;
        BtnSave->Font->Size = MainFontSize;
        cbDirectIP->Font->Color = TextColor;
        cbDirectIP->Font->Name = MainFont;
        cbDirectIP->Font->Size = MainFontSize;
        BtnDeaultIP->Font->Color = TextBGColor;
        BtnDeaultIP->Font->Name = MainFont;
        BtnDeaultIP->Font->Size = MainFontSize;
        BtnSearching->Font->Color = TextBGColor;
        BtnSearching->Font->Name = MainFont;
        BtnSearching->Font->Size = MainFontSize;
        BtnSetting->Font->Color = TextBGColor;
        BtnSetting->Font->Name = MainFont;
        BtnSetting->Font->Size = MainFontSize;
        BtnReset->Font->Color = TextBGColor;
        BtnReset->Font->Name = MainFont;
        BtnReset->Font->Size = MainFontSize;
        BtnAdd->Font->Color = TextBGColor;
        BtnAdd->Font->Name = MainFont;
        BtnAdd->Font->Size = MainFontSize;
        pnlaModeInfor->Color = MainColor;
        pnlaModeInfor->Font->Color = TextColor;
        pnlaModeInfor->Font->Name = MainFont;
        pnlaModeInfor->Font->Size = MainFontSize;
        pnModeInfor->Color = MainColor;
        pnModeInfor->Font->Color = TextColor;
        pnModeInfor->Font->Name = MainFont;
        pnModeInfor->Font->Size = MainFontSize;
        laComm->Font->Color = TextColor;
        laComm->Font->Name = MainFont;
        laComm->Font->Size = MainFontSize;
        cbEthernet->Font->Color = TextColor;
        cbEthernet->Font->Name = MainFont;
        cbEthernet->Font->Size = MainFontSize;
        cbWiFi->Font->Color = TextColor;
        cbWiFi->Font->Name = MainFont;
        cbWiFi->Font->Size = MainFontSize;
        cbBluetooth->Font->Color = TextColor;
        cbBluetooth->Font->Name = MainFont;
        cbBluetooth->Font->Size = MainFontSize;
        pnSettingInfor->Color = MainColor;
        pnSettingInfor->Font->Color = TextColor;
        pnSettingInfor->Font->Name = MainFont;
        pnSettingInfor->Font->Size = MainFontSize;
        pnlaSettingInfor->Color = MainColor;
        pnlaSettingInfor->Font->Color = TextColor;
        pnlaSettingInfor->Font->Name = MainFont;
        pnlaSettingInfor->Font->Size = MainFontSize;
        laDeviceConnectPort->Font->Color = TextColor;
        laDeviceConnectPort->Font->Name = MainFont;
        laDeviceConnectPort->Font->Size = MainFontSize;
        cbDeviceConnectPort->Font->Color = TextColor;
        cbDeviceConnectPort->Font->Name = MainFont;
        cbDeviceConnectPort->Font->Size = MainFontSize;
        laDebuggingState->Font->Color = TextColor;
        laDebuggingState->Font->Name = MainFont;
        laDebuggingState->Font->Size = MainFontSize;
        cbDebuggingState->Font->Color = TextColor;
        cbDebuggingState->Font->Name = MainFont;
        cbDebuggingState->Font->Size = MainFontSize;
        laDeviceBaudrate->Font->Color = TextColor;
        laDeviceBaudrate->Font->Name = MainFont;
        laDeviceBaudrate->Font->Size = MainFontSize;
        cbDeviceBaudrate->Font->Color = TextColor;
        cbDeviceBaudrate->Font->Name = MainFont;
        cbDeviceBaudrate->Font->Size = MainFontSize;
        pnlaDataPacking->Color = MainColor;
        pnlaDataPacking->Font->Color = TextColor;
        pnlaDataPacking->Font->Name = MainFont;
        pnlaDataPacking->Font->Size = MainFontSize;
        pnDataPacking->Color = MainColor;
        pnDataPacking->Font->Color = TextColor;
        pnDataPacking->Font->Name = MainFont;
        pnDataPacking->Font->Size = MainFontSize;
        laTimeOut->Font->Color = TextColor;
        laTimeOut->Font->Name = MainFont;
        laTimeOut->Font->Size = MainFontSize;
        laTimeOut2->Font->Color = TextColor;
        laTimeOut2->Font->Name = MainFont;
        laTimeOut2->Font->Size = MainFontSize;
        laEndTextHex1->Font->Color = TextColor;
        laEndTextHex1->Font->Name = MainFont;
        laEndTextHex1->Font->Size = MainFontSize;
        laEndTextHex2->Font->Color = TextColor;
        laEndTextHex2->Font->Name = MainFont;
        laEndTextHex2->Font->Size = MainFontSize;
        laEndTextASCII1->Font->Color = TextColor;
        laEndTextASCII1->Font->Name = MainFont;
        laEndTextASCII1->Font->Size = MainFontSize;
        laEndTextASCII2->Font->Color = TextColor;
        laEndTextASCII2->Font->Name = MainFont;
        laEndTextASCII2->Font->Size = MainFontSize;
        pnlaFirmwareInfor->Color = MainColor;
        pnlaFirmwareInfor->Font->Color = TextColor;
        pnlaFirmwareInfor->Font->Name = MainFont;
        pnlaFirmwareInfor->Font->Size = MainFontSize;
        laFirmwareInfor->Color = MainColor;
        laFirmwareInfor->Font->Color = TextColor;
        laFirmwareInfor->Font->Name = MainFont;
        laFirmwareInfor->Font->Size = MainFontSize;
        BtnFirmwareRead->Font->Color = TextBGColor;
        BtnFirmwareRead->Font->Name = MainFont;
        BtnFirmwareRead->Font->Size = MainFontSize;
        BtnFirmwareSet->Font->Color = TextBGColor;
        BtnFirmwareSet->Font->Name = MainFont;
        BtnFirmwareSet->Font->Size = MainFontSize;
        BtnBLEStop->Font->Color = TextBGColor;
        BtnBLEStop->Font->Name = MainFont;
        BtnBLEStop->Font->Size = MainFontSize;
        BtnClose->Font->Color = TextBGColor;
        BtnClose->Font->Name = MainFont;
        BtnClose->Font->Size = MainFontSize;

        /*TTntLabel *tLabel;
        try{
            for(int i = 0; i < 14; i++)
            {
                tLabel = dynamic_cast<TTntLabel *>(FindComponent("Label" + IntToStr(i + 1)));
                if(tLabel)
                {
                    tLabel->Font->Color = TextColor;
                    tLabel->Font->Name = MainFont;
                    tLabel->Font->Size = MainFontSize;
                }
            }
        }catch(...){
        }*/
        pnBtnConnect->Color = ButtonColor;
        pnBtnSystem->Color = ButtonColor;
        pnBtnNetworkAdapter->Color = ButtonColor;
        pnBtnDB300Info->Color = ButtonColor;
        pnBtnSave->Color = ButtonColor;
        pnBtnDeaultIP->Color = ButtonColor;
        pnBtnSearching->Color = ButtonColor;
        pnBtnSetting->Color = ButtonColor;
        pnBtnAdd->Color = ButtonColor;
        pnBtnReset->Color = ButtonColor;
        pnBtnFirmwareRead->Color = ButtonColor;
        pnBtnFirmwareSet->Color = ButtonColor;
        pnBtnBLEStop->Color = ButtonColor;
        pnBtnClose->Color = ButtonColor;

        pnMainDivide->Color = MainDivideColor;
        pnDivide->Color = MainDivideColor;
        pnDivide2->Color = MainDivideColor;
        gbLAN->Color = MainRectangleColor;
        rgComMode->Color = MainRectangleColor;
        rgOperationMode->Color = MainRectangleColor;
        gbServer->Color = MainRectangleColor;
        gbWiFiModeSetting->Color = MainRectangleColor;
        gbAPMode->Color = MainRectangleColor;
        gbMainInterface->Color = MainRectangleColor;
        gbSubInterface->Color = MainRectangleColor;
        gbModeInfor->Color = MainRectangleColor;
        gbSettingInfor->Color = MainRectangleColor;
        gbFirmwareInfor->Color = MainRectangleColor;
        gbDataPacking->Color = MainRectangleColor;
        gbUDP->Color = MainRectangleColor;
        gbUDPMode->Color = MainRectangleColor;
        gbAdvanced->Color = MainRectangleColor;
        gbSerial->Color = MainRectangleColor;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::FormCreate(TObject *Sender)
{
        //IdUDPClient->Active = false;
        //IdUDPClient->Host = "255.255.255.255";
        //IdUDPClient->Port = UDPClientPort;
        bClick = false;
        this->Caption = "DabitNet(V" + FormMain->VerMj[0] + "." + FormMain->VerMj[1] + "." + FormMain->VerMj[2] + ")";
        pnCaption->Caption = "  " + this->Caption;
        RoadIniFile();
        IdUDPServer->Bindings->Clear();
        IdUDPServer->BroadcastEnabled = true;
        TIdSocketHandle * SH;
        try{
            SH = IdUDPServer->Bindings->Add();
            SH->Port = UDPServerPort;
            //IdUDPServer->Active = true;
        }catch(...){
        }
        eDirectIPAddr->Text = asDirectIPAddress[0];
        eDirectIPAddr->Visible = false;
        cbDirectIPAddr->Clear();
        for(int i = 0; i < 10; i++)
        {
            if(asDirectIPAddress[i] != "" && asDirectIPAddress[i] != " ")
                cbDirectIPAddr->Items->Add(asDirectIPAddress[i]);
        }
        cbDirectIPAddr->ItemIndex = DirectIPIndex;
        if(cbDirectIPAddr->ItemIndex < 0)
            cbDirectIPAddr->ItemIndex = 0;
        eDirectPort->Text = IntToStr(SIPPort);
        ComBox1->Text = FormMain->PortUse1;
        ComBox2->Text = FormMain->PortUse1;
        BaudBox1->ItemIndex = FormMain->BaudRateUse1;
        BaudBox2->ItemIndex = FormMain->BaudRateUse1;
        cbDTR->ItemIndex = 0;//FormMain->DTR1;
        cbRTS->ItemIndex = 0;//FormMain->RTS1;
        //LONG style1 = GetWindowLong(Edit4->Handle,GWL_STYLE) | ES_RIGHT;
        //LONG style2 = GetWindowLong(Edit3->Handle,GWL_STYLE) | ES_CENTER;
        //SetWindowLong(Edit3->Handle,GWL_STYLE,style2);
        //SetWindowLong(Edit4->Handle,GWL_STYLE,style1);
        //SetWindowLong(Edit5->Handle,GWL_STYLE,style1);
        Edit7->Text = IntToStr(UDPClientPort);
        Edit8->Text = IntToStr(UDPServerPort);
        SearchComPort();
        //UDPType = 1;
        cbSearchTarget->ItemIndex = 0;//UDPType;
        if(cbSearchTarget->ItemIndex < 0)
            cbSearchTarget->ItemIndex = 0;
        if(UDPType == 1)
            rbOnlyEth->Checked = true;
        else if(UDPType == 2)
            rbOnlyWiFi->Checked = true;
        else
            rbBoth->Checked = true;
        cbResponseType->ItemIndex = UDPUnicast;
        cbUDPUnicast->Checked = UDPUnicast;
        pnInfor->Visible = DB300Visible;
        cbDB300Tab->ItemIndex = DB300Visible;
        if(DabitNetTap == 1)
            tsWiFi->Click();
        else if(DabitNetTap == 2)
            tsOption->Click();
        else if(DabitNetTap == 3)
            tsInfor->Click();
        else
            tsNetwork->Click();
        //etMACAddr->Text = LastMac;
        laMACAddr->Caption = LastMac;
        ComKind = 1;
        asNetworkName = GetNetwork(1);//GetDNS(1);
        if(ComKind == 0)
        {
            laComKind->Caption = "Serial : " + FormMain->PortUse1;
            pnComKind->Caption = laComKind->Caption;
            if(ComPortSet(ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, "", SIPPort, true))
                pnComKind->Font->Color = clRed;
            else
                pnComKind->Font->Color = clSilver;
            rbSerial->Checked = true;
        }
        else
        {
            laComKind->Caption = "UDP : " + asNetworkName;
            pnComKind->Caption = laComKind->Caption;
            rbUDP->Checked = true;
            try{
                IdUDPServer->Active = true;
                pnComKind->Font->Color = clSilver;
            }catch(...){
                pnComKind->Font->Color = clRed;
            }
        }
        LONG style = GetWindowLong(eIPAddr->Handle, GWL_STYLE) | ES_CENTER;
        SetWindowLong(eIPAddr->Handle, GWL_STYLE, style);
        SetWindowLong(ePort->Handle, GWL_STYLE, style);
        SetWindowLong(eSubnetMask->Handle, GWL_STYLE, style);
        SetWindowLong(eGateway->Handle, GWL_STYLE, style);
        SetWindowLong(eServerIPAddr->Handle, GWL_STYLE, style);
        SetWindowLong(eServerName->Handle, GWL_STYLE, style);
        SetWindowLong(eServerPort->Handle, GWL_STYLE, style);
        SetWindowLong(eSSID->Handle, GWL_STYLE, style);
        SetWindowLong(eSSIDPW->Handle, GWL_STYLE, style);
        SetWindowLong(eIPAddr->Handle, GWL_STYLE, style);
        //style = GetWindowLong(eInterval->Handle, GWL_STYLE) | ES_RIGHT;
        SetWindowLong(eInterval->Handle, GWL_STYLE, style);
        SetWindowLong(Edit7->Handle, GWL_STYLE, style);
        SetWindowLong(Edit8->Handle, GWL_STYLE, style);
        //SetWindowLong(ComBox2->Handle, GWL_STYLE, style);
        //SetWindowLong(BaudBox2->Handle, GWL_STYLE, style);
        //unsigned oldalign = SetTextAlign(ComBox2->Canvas->Handle, TA_CENTER);
        //TRect Rect = ComBox2->BoundsRect;
        //ComBox2->Canvas->TextRect(Rect, (Rect.Right+Rect.Left)/2, Rect.Top + 2, ComBox2->Text);
        //SetTextAlign(ComBox2->Canvas->Handle, oldalign);
        bClick = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::IdUDPServerUDPRead(TObject *Sender,
      TStream *AData, TIdSocketHandle *ABinding)
{
        if(!ABinding->HandleAllocated)
            return;
        char Buf[2048];
        ZeroMemory(Buf, 2048);
        AData->ReadBuffer(Buf, AData->Size);
        if(!AData->Size && AData->Size > 2048)
        {
            //ShowMessage(" 데이터 오버플로우");
            return;
        }
        else if(AData->Size > 0)
        {
            RecieveData(Buf, AData->Size);
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::RecieveData(char *Buf, int iBufSize)
{
        if((Buf[0] == 'D' && Buf[1] == 'I' && Buf[2] == 'B' && Buf[3] == 'D'))// || bSerial)
        {
            bAddKind = 0;
            if((BYTE)Buf[135] == 0x0d && (BYTE)Buf[136] == 0x0a)
                bAddKind = 2;
            else if((BYTE)Buf[109] == 0x0d && (BYTE)Buf[110] == 0x0a)
                bAddKind = 1;
            if(bAddKind > 0)
            {
                if(bAddKind > 1)
                    MACIPData[MACCnt] = AnsiString(Buf).SubString(1, 222);
                else
                    MACIPData[MACCnt] = AnsiString(Buf).SubString(1, 111);
                bool bSame = false;
                for(int i = 0; i < MACCnt; i++)
                {
                    if(MACIPData[i] == MACIPData[MACCnt])
                    {
                        bSame = true;
                        MACIPData[MACCnt] = "";
                        break;
                    }
                }
                if(bSame)
                    return;
                if(iBufSize <= 60)
                    return;
                AnsiString asMac = AnsiString(Buf).SubString(7, 17);
                AnsiString temp = "";
                AnsiString temp2 = "";
                mMACAddr->Items->Add(asMac);
                eMACAddr->Text = asMac;
                mMACAddr->ItemIndex = -1;
                //mMACAddrClick(this);
                MACCnt++;
                SchBmpFile = true;
            }
        }
        else if((Buf[0] == 'I' && Buf[1] == 'N' && Buf[2] == 'F' && Buf[3] == 'O'))
        {
            if(iBufSize <= 60)
                return;
            AnsiString asInfor = AnsiString(Buf).SubString(1, 70);
            AnsiString asMac = asInfor.SubString(28, 1);
            cbDebuggingState->ItemIndex = StrToIntDef(asMac, 0);
            asMac = asInfor.SubString(29, 1);
            cbDeviceConnectPort->ItemIndex = StrToIntDef(asMac, 0);
            asMac = asInfor.SubString(30, 1);
            cbDeviceBaudrate->ItemIndex = StrToIntDef(asMac, 4);
            asMac = asInfor.SubString(31, 1);
            eEndTextASCII1->Text = asMac;
            asMac = asInfor.SubString(32, 1);
            eEndTextASCII2->Text = asMac;
            asMac = asInfor.SubString(33, 2);
            eEndTextHex1->Text = asMac;
            asMac = asInfor.SubString(35, 2);
            eEndTextHex2->Text = asMac;
            try{
                asMac = IntToStr(StrToInt(asInfor.SubString(37, 3)));
            }catch(...){
                asMac = "";
            }
            eTimeOut->Text = asMac;
            asMac = asInfor.SubString(48, 1);
            cbEthernet->Checked = false;
            laComm->Caption = "";
            if(asMac.UpperCase() == "E")
            {
                cbEthernet->Checked = true;
                laComm->Caption = cbEthernet->Caption;
            }
            asMac = asInfor.SubString(49, 1);
            cbWiFi->Checked = false;
            if(asMac.UpperCase() == "W")
            {
                cbWiFi->Checked = true;
                if(laComm->Caption != (WideString)"")
                    laComm->Caption = laComm->Caption + " & " + cbWiFi->Caption;
                else
                    laComm->Caption = cbWiFi->Caption;
            }
            asMac = asInfor.SubString(50, 1);
            cbBluetooth->Checked = false;
            if(asMac.UpperCase() == "B")
            {
                cbBluetooth->Checked = true;
                if(laComm->Caption != (WideString)"")
                    laComm->Caption = laComm->Caption + " & " + cbBluetooth->Caption;
                else
                    laComm->Caption = cbBluetooth->Caption;
            }
            laFirmwareInfor->Caption = asInfor.SubString(52, 17);
        }
        //if(FormMain->ShowDibdnetLogView)
        //{
        try{
            AnsiString asMac = "";
            if(ComKind == 0)
            {     
                for(int i = 0; i < iBufSize; i++)
                      asMac = asMac + FormMain->Byte2HexConvert(Buf[i]) + " ";
                asMac = asMac + "\r\n" + AnsiString((char*)Buf).SubString(1, iBufSize);
                FormMain->AddLog("Serial Recieve Data : " + asMac.SubString(1, iBufSize * 4), clBlue);
            }
            else
            {
                //if(Buf[25] == 'R' && Buf[26] == 'E' && Buf[27] == 'S' && Buf[28] == 'E' && Buf[29] == 'T')
                //{}
                //else
                //{
                    for(int i = 0; i < iBufSize; i++)
                      asMac = asMac + FormMain->Byte2HexConvert(Buf[i]) + " ";
                //}
                asMac = asMac + "\r\n" + AnsiString((char*)Buf).SubString(1, iBufSize);
                if(cbDirectIP->Checked)
                     FormMain->AddLog("TCP/IP Recieve Data : " + asMac.SubString(1, iBufSize * 4 + 2), clBlue);
                else
                     FormMain->AddLog("UDP Recieve Data : " + asMac.SubString(1, iBufSize * 4 + 2), clBlue);
            }
        }catch(...){
        }
        //}
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BtnSearchingClick(TObject *Sender)
{
        BtnSearching->Enabled = false;
        mMACAddr->Clear();
        eMemo->Text = "";
        eIPAddr->Text = "";
        eSubnetMask->Text = "";
        eGateway->Text = "";
        ePort->Text = "";
        //rgComMode->ItemIndex = -1;
        rgStatic->Checked = false;
        rgDHCP->Checked = false;
        eServerIPAddr->Text = "";
        eServerPort->Text = "";
        eInterval->Text = "";
        eServerName->Text = "";
        //rgOperationMode->ItemIndex = -1;
        rbServer->Checked = false;
        rbClient->Checked = false;
        eSSID->Text = "";
        eSSIDPW->Text = "";
        //cbAPMode->ItemIndex = -1;
        rbSTA->Checked = false;
        rbAP->Checked = false;
        rbEth->Checked = false;
        //cbWiFiMode->ItemIndex = -1;
        rbBluetooth->Checked = false;
        rbWiFi->Checked = false;
        rbEthernetSet->Checked = false;
        rbWiFiSet->Checked = false;
        cbWiFiSet->Checked = false;
        //mMACAddr->Items->Add("12:34:56:78:90");
        MACCnt = 0;
        AnsiString asSendData = "SEARCHING DIBD\r\n";
        if(UDPUnicast)
            asSendData = "SEARCHING DIBD  U\r\n";
        else
            asSendData = "SEARCHING DIBD  B\r\n";
        BYTE SendData[2048];
        int SendSize = asSendData.Length();
        ZeroMemory(SendData, sizeof(SendData));
        memcpy(SendData, asSendData.c_str(), SendSize);
        /*IdUDPClient->Active = true;
        IdUDPClient->ReceiveTimeout = 10;
        IdUDPClient->BroadcastEnabled = true;
        IdUDPClient->SendBuffer(SendData, SendSize);
        IdUDPClient->Active = false;*/
        AnsiString asMac = "255.255.255.255";
        AnsiString asMessage = "";
        UDPClientPort = StrToIntDef(AnsiString(Edit7->Text).TrimRight(), 5108);
        UDPServerPort = StrToIntDef(AnsiString(Edit8->Text).TrimRight(), 5109);
        AnsiString asLog = "";
        iSerialPos = 0;
        bAddKind = 5;
        try{
            if(cbDirectIP->Checked)
            {
                //asMac = eDirectIPAddr->Text;
                SIPPort = StrToInt(eDirectPort->Text);
                bool bDDNS = false;
                asMac = cbDirectIPAddr->Text;
                int tempIP = StrToIntDef(asMac.SubString(1, asMac.Pos(".") - 1), -1);
                if(tempIP < 0)
                    bDDNS = true;
                if(!bDDNS)
                {
                    char Buf[6];
                    AnsiString asMac2 = "";
                    int temp = 0;
                    AnsiString temp2 = "";
                    int iPos = 0;
                    int iDelPos = 0;
                    for(int i = 0; i < 4; i++)
                    {
                        if(i == 3)
                            iPos = asMac.Length();
                        else
                            iPos = asMac.Pos(".") - 1;
                        iDelPos = iPos + 1;
                        if(iPos > 3)
                            iPos = 3;
                        try{
                            temp = StrToInt(asMac.SubString(1, iPos));
                        }catch(...){
                            asMessage = "Wrong Local IP Address";
                            FormMain->Message("\r\n" + asMessage + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                            BtnSearching->Enabled = true;
                            return;
                        }
                        ZeroMemory(Buf, sizeof(Buf));
                        wsprintf(Buf, "%03d", temp);
                        asMac = asMac.Delete(1, iDelPos);
                        if(i == 3)
                        {
                            temp2 = temp2 + AnsiString(Buf).SubString(1, 3);
                            asMac2 = asMac2 + IntToStr(temp);
                        }
                        else
                        {
                            temp2 = temp2 + AnsiString(Buf).SubString(1, 3) + ".";
                            asMac2 = asMac2 + IntToStr(temp) + ".";
                        }
                    }
                    //eDirectIPAddr->Text = asMac2;
                    cbDirectIPAddr->Text = asMac2;
                    asMac = asMac2;
                }
                //FormMain->ComClose(FormMain->Setup.ComKind);
                bStopSend = false;
                for(int i = 0; i < SendSize; i++)
                    asLog = asLog + Int2HexConvert(SendData[i]) + " ";
                asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
                if(ComPortSet(1, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, asMac, SIPPort, false))
                {
                    bStopSend = false;
                    ModuleData(1, FormMain->Controler1, SendData, SendSize, 0, AN, false);
                    pnComKind->Font->Color = clSilver;
                    FormMain->AddLog("TCP/IP Send Data : " + asLog, clBlue);
                    ComClose(1);
                }
                else
                    pnComKind->Font->Color = clRed;
            }
            else
            {
                if(ComKind == 0)
                {
                    asSendData = "++SET++![SEARCHING DIBD\r\n!]";
                    if(UDPUnicast)
                        asSendData = "++SET++![SEARCHING DIBD  U\r\n!]";
                    else
                        asSendData = "++SET++![SEARCHING DIBD  B\r\n!]";
                    SendSize = asSendData.Length();
                    ZeroMemory(SendData, sizeof(SendData));
                    memcpy(SendData, asSendData.c_str(), SendSize);
                    for(int i = 0; i < SendSize; i++)
                        asLog = asLog + Int2HexConvert(SendData[i]) + " ";
                    asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
                    if(ComPortSet(ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, asMac, SIPPort, false))
                    {
                        pnComKind->Font->Color = clSilver;
                        ComPort1->Write(SendData, SendSize);
                        FormMain->AddLog("Serial Send Data : " + asLog, clBlue);
                    }
                    else
                       pnComKind->Font->Color = clRed;
                }
                else
                {
                    for(int i = 0; i < SendSize; i++)
                        asLog = asLog + Int2HexConvert(SendData[i]) + " ";
                    asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
                    IdUDPServer->SendBuffer(asMac, UDPClientPort, SendData, SendSize);
                    if(rbBoth->Checked)
                    {
                        NoneDelayCount(100);
                        IdUDPServer->SendBuffer(asMac, 5107, SendData, SendSize);
                    }
                    pnComKind->Font->Color = clSilver;
                    FormMain->AddLog("UDP Send Data : " + asLog, clBlue);
                }
            }
        }catch(...){
        }
        bool bSame = false;
        for(int i = 0; i < cbDirectIPAddr->Items->Count; i++)
        {
            if(cbDirectIPAddr->Text == asDirectIPAddress[i])
                bSame = true;
        }
        if(!bSame)
        {
            if(cbDirectIPAddr->Items->Count < 10)
            {
                cbDirectIPAddr->Items->Add(cbDirectIPAddr->Text);
                cbDirectIPAddr->ItemIndex = cbDirectIPAddr->Items->Count - 1;
                DirectIPCnt = cbDirectIPAddr->ItemIndex;
                DirectIPIndex = cbDirectIPAddr->ItemIndex;
            }
            else
            {
                if(DirectIPCnt >= 9)
                    DirectIPCnt = 0;
                else
                    DirectIPCnt++;
                cbDirectIPAddr->Items->Strings[DirectIPCnt] = cbDirectIPAddr->Text;
                DirectIPIndex = DirectIPCnt;
            }
            asDirectIPAddress[DirectIPCnt] = cbDirectIPAddr->Text;
        }
        NoneDelayCount(500);
        if(rbBoth->Checked)
            NoneDelayCount(1000);
        if(ComKind == 0)
        {
            SerialDelayCount(500);
            ComClose(ComKind);
        }
        int iPos = 0;
        if(laMACAddr->Caption == (WideString)"")
        {
            if(mMACAddr->Count > 0)
            {
                mMACAddr->ItemIndex = 0;
                mMACAddrClick(this);
            }
        }
        else
        {
            for(int i = 0; i < mMACAddr->Count; i++)
            {
                iPos = 0;
                asMac = MACIPData[i].SubString(7, 17);
                /*for(int j = 0; j < asMac.Length(); j++)
                {
                    iPos = asMac.Pos("-");
                    if(iPos)
                        asMac = asMac.Delete(iPos, 1);
                    else
                        break;
                }*/
                if(laMACAddr->Caption == asMac)
                {
                    mMACAddr->ItemIndex = i;
                    mMACAddrClick(this);
                    break;
                }
            }
        }
        this->BringToFront();
        this->Show();
        BtnSearching->Enabled = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BtnFirmwareReadClick(TObject *Sender)
{
        BtnFirmwareRead->Enabled = false;
        AnsiString asSendData = "";
        BYTE SendData[2048];
        //int TotMACnt = 0;
        int CurMAC = 0;
        int SendSize = asSendData.Length();
        CurMAC = mMACAddr->ItemIndex;
        AnsiString asSrcMAC = "";
        AnsiString asMac = "";
        AnsiString asMessage = "";
        AnsiString asLog = "";
        UDPClientPort = StrToIntDef(AnsiString(Edit7->Text).TrimRight(), 5108);
        UDPServerPort = StrToIntDef(AnsiString(Edit8->Text).TrimRight(), 5109);
        cbEthernet->Checked = false;
        cbWiFi->Checked = false;
        cbBluetooth->Checked = false;
        cbDebuggingState->ItemIndex = -1;
        cbDeviceConnectPort->ItemIndex = -1;
        cbDeviceBaudrate->ItemIndex = -1;
        eEndTextASCII1->Text = "";
        eEndTextASCII2->Text = "";
        eEndTextHex1->Text = "";
        eEndTextHex2->Text = "";
        eTimeOut->Text = "";
        laComm->Caption = "";
        laFirmwareInfor->Caption = "";
        iSerialPos = 0;
        bAddKind = 6;
        try{
            asSrcMAC = MACIPData[CurMAC];
            asMac = asSrcMAC.SubString(7, 17);
            asSendData = "INFO_R  " + asMac + "\r\n";
            SendSize = asSendData.Length();
            ZeroMemory(SendData, sizeof(SendData));
            memcpy(SendData, asSendData.c_str(), SendSize);
            AnsiString asMac = "";
            asMac = "255.255.255.255";
            if(cbDirectIP->Checked)
            {
                SIPPort = StrToInt(eDirectPort->Text);
                bool bDDNS = false;
                asMac = cbDirectIPAddr->Text;
                int tempIP = StrToIntDef(asMac.SubString(1, asMac.Pos(".") - 1), -1);
                if(tempIP < 0)
                    bDDNS = true;
                if(!bDDNS)
                {
                    AnsiString asMac2 = "";
                    int temp = 0;
                    AnsiString temp2 = "";
                    int iPos = 0;
                    int iDelPos = 0;
                    char Buf[6];
                    for(int i = 0; i < 4; i++)
                    {
                        if(i == 3)
                            iPos = asMac.Length();
                        else
                            iPos = asMac.Pos(".") - 1;
                        iDelPos = iPos + 1;
                        if(iPos > 3)
                            iPos = 3;
                        try{
                            temp = StrToInt(asMac.SubString(1, iPos));
                        }catch(...){
                            //ShowMessage("Wrong Local IP Address");
                            asMessage = "Wrong Local IP Address";
                            FormMain->Message("\r\n" + asMessage + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                            BtnReset->Enabled = true;
                            return;
                        }
                        ZeroMemory(Buf, sizeof(Buf));
                        wsprintf(Buf, "%03d", temp);
                        asMac = asMac.Delete(1, iDelPos);
                        if(i == 3)
                        {
                            temp2 = temp2 + AnsiString(Buf).SubString(1, 3);
                            asMac2 = asMac2 + IntToStr(temp);
                        }
                        else
                        {
                            temp2 = temp2 + AnsiString(Buf).SubString(1, 3) + ".";
                            asMac2 = asMac2 + IntToStr(temp) + ".";
                        }
                    }
                    //eDirectIPAddr->Text = asMac2;
                    cbDirectIPAddr->Text = asMac2;
                    asMac = asMac2;
                }
                //FormMain->ComClose(FormMain->Setup.ComKind);
                bStopSend = false;
                for(int i = 0; i < SendSize; i++)
                    asLog = asLog + Int2HexConvert(SendData[i]) + " ";
                asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
                if(ComPortSet(1, PortUse1, BaudRateUse1, Disable, Disable, asMac, SIPPort, false))
                {
                    bStopSend = false;
                    ModuleData(ComKind, FormMain->Controler1, SendData, SendSize, 0, AN, false);
                    FormMain->AddLog("TCP/IP Send Data : " + asLog, clBlue);
                    pnComKind->Font->Color = clSilver;
                    ComClose(1);
                }
                else
                    pnComKind->Font->Color = clRed;
            }
            else
            {
                if(ComKind == 0)
                {
                    asSendData = "++SET++![" + asSendData + "!]";
                    SendSize = asSendData.Length();
                    ZeroMemory(SendData, sizeof(SendData));
                    memcpy(SendData, asSendData.c_str(), SendSize);
                    for(int i = 0; i < SendSize; i++)
                        asLog = asLog + Int2HexConvert(SendData[i]) + " ";
                    asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
                    if(ComPortSet(ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, asMac, SIPPort, false))
                    {
                        ComPort1->Write(SendData, SendSize);
                        pnComKind->Font->Color = clSilver;
                        FormMain->AddLog("Serial Send Data : " + asLog, clBlue);
                    }
                    else
                        pnComKind->Font->Color = clRed;
                }
                else
                {
                    for(int i = 0; i < SendSize; i++)
                        asLog = asLog + Int2HexConvert(SendData[i]) + " ";
                    asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
                    IdUDPServer->SendBuffer(asMac, UDPClientPort, SendData, SendSize);
                    if(rbBoth->Checked)
                    {
                        NoneDelayCount(200);
                        IdUDPServer->SendBuffer(asMac, 5107, SendData, SendSize);
                    }
                    pnComKind->Font->Color = clSilver;
                    FormMain->AddLog("UDP Send Data : " + asLog, clBlue);
                }
            }
        }catch(...){
        }
        NoneDelayCount(500);
        if(ComKind == 0)
        {
            SerialDelayCount(500);
            ComClose(ComKind);
        }
        this->BringToFront();
        this->Show();
        BtnFirmwareRead->Enabled = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BtnFirmwareSetClick(TObject *Sender)
{
        BtnFirmwareSet->Enabled = false;
        AnsiString asSendData = "";
        BYTE SendData[2048];
        int temp = 0;
        int CurMAC = 0;
        int SendSize = 0;
        CurMAC = mMACAddr->ItemIndex;
        AnsiString asSrcMAC = "";
        AnsiString asMac = "";
        AnsiString asMessage = "";
        AnsiString asLog = "";
        UDPClientPort = StrToIntDef(AnsiString(Edit7->Text).TrimRight(), 5108);
        UDPServerPort = StrToIntDef(AnsiString(Edit8->Text).TrimRight(), 5109);
        try{
            asSrcMAC = MACIPData[CurMAC];
            asMac = asSrcMAC.SubString(7, 17);
            asSendData = "INFO_W  " + asMac + "  ";
            ZeroMemory(SendData, sizeof(SendData));
            if(cbDebuggingState->ItemIndex < 0)
                cbDebuggingState->ItemIndex = 0;
            asSendData = asSendData + IntToStr(cbDebuggingState->ItemIndex);
            if(cbDeviceConnectPort->ItemIndex < 0)
                cbDeviceConnectPort->ItemIndex = 0;
            asSendData = asSendData + IntToStr(cbDeviceConnectPort->ItemIndex);
            if(cbDeviceBaudrate->ItemIndex < 0)
                cbDeviceBaudrate->ItemIndex = 0;
            asSendData = asSendData + IntToStr(cbDeviceBaudrate->ItemIndex);
            asSendData = asSendData + eEndTextASCII1->Text + eEndTextASCII2->Text;
            asSendData = asSendData + AnsiString(eEndTextHex1->Text).UpperCase() + AnsiString(eEndTextHex2->Text).UpperCase();
            BYTE Buf[4];
            ZeroMemory(Buf, sizeof(Buf));
            temp = StrToIntDef(eTimeOut->Text, 10);
            wsprintf(Buf, "%03d", temp);
            asSendData = asSendData + AnsiString((char*)Buf).SubString(1, 3) + "      \r\n";
            SendSize = asSendData.Length();
            memcpy(SendData, asSendData.c_str(), SendSize);
            asMac = "";
            asMac = "255.255.255.255";
            if(cbDirectIP->Checked)
            {
                SIPPort = StrToInt(eDirectPort->Text);
                bool bDDNS = false;
                asMac = cbDirectIPAddr->Text;
                int tempIP = StrToIntDef(asMac.SubString(1, asMac.Pos(".") - 1), -1);
                if(tempIP < 0)
                    bDDNS = true;
                if(!bDDNS)
                {
                    AnsiString asMac2 = "";
                    int temp = 0;
                    AnsiString temp2 = "";
                    int iPos = 0;
                    int iDelPos = 0;
                    char Buf[6];
                    for(int i = 0; i < 4; i++)
                    {
                        if(i == 3)
                            iPos = asMac.Length();
                        else
                            iPos = asMac.Pos(".") - 1;
                        iDelPos = iPos + 1;
                        if(iPos > 3)
                            iPos = 3;
                        try{
                            temp = StrToInt(asMac.SubString(1, iPos));
                        }catch(...){
                            //ShowMessage("Wrong Local IP Address");
                            asMessage = "Wrong Local IP Address";
                            FormMain->Message("\r\n" + asMessage + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                            BtnReset->Enabled = true;
                            return;
                        }
                        ZeroMemory(Buf, sizeof(Buf));
                        wsprintf(Buf, "%03d", temp);
                        asMac = asMac.Delete(1, iDelPos);
                        if(i == 3)
                        {
                            temp2 = temp2 + AnsiString(Buf).SubString(1, 3);
                            asMac2 = asMac2 + IntToStr(temp);
                        }
                        else
                        {
                            temp2 = temp2 + AnsiString(Buf).SubString(1, 3) + ".";
                            asMac2 = asMac2 + IntToStr(temp) + ".";
                        }
                    }
                    //eDirectIPAddr->Text = asMac2;
                    cbDirectIPAddr->Text = asMac2;
                    asMac = asMac2;
                }
                //FormMain->ComClose(FormMain->Setup.ComKind);
                bStopSend = false;
                for(int i = 0; i < SendSize; i++)
                    asLog = asLog + Int2HexConvert(SendData[i]) + " ";
                asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
                if(ComPortSet(1, PortUse1, BaudRateUse1, Disable, Disable, asMac, SIPPort, false))
                {
                    bStopSend = false;
                    ModuleData(ComKind, FormMain->Controler1, SendData, SendSize, 0, AN, false);
                    FormMain->AddLog("TCP/IP Send Data : " + asLog, clBlue);
                    pnComKind->Font->Color = clSilver;
                    ComClose(1);
                }
                else
                    pnComKind->Font->Color = clRed;
            }
            else
            {  
                if(ComKind == 0)
                {
                    asSendData = "++SET++![" + asSendData + "!]";
                    SendSize = asSendData.Length();
                    ZeroMemory(SendData, sizeof(SendData));
                    memcpy(SendData, asSendData.c_str(), SendSize);
                    for(int i = 0; i < SendSize; i++)
                        asLog = asLog + Int2HexConvert(SendData[i]) + " ";
                    asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
                    if(ComPortSet(ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, asMac, SIPPort, false))
                    {
                        ComPort1->Write(SendData, SendSize);
                        pnComKind->Font->Color = clSilver;
                        FormMain->AddLog("Serial Send Data : " + asLog, clBlue);
                    }
                    else
                        pnComKind->Font->Color = clRed;
                }
                else
                {
                    for(int i = 0; i < SendSize; i++)
                        asLog = asLog + Int2HexConvert(SendData[i]) + " ";
                    asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
                    IdUDPServer->SendBuffer(asMac, UDPClientPort, SendData, SendSize);
                    if(rbBoth->Checked)
                    {
                        NoneDelayCount(200);
                        IdUDPServer->SendBuffer(asMac, 5107, SendData, SendSize);
                    }
                    pnComKind->Font->Color = clSilver;
                    FormMain->AddLog("UDP Send Data : " + asLog, clBlue);
                }
            }
        }catch(...){
        }
        if(ComKind == 0)
        {
            NoneDelayCount(500);
            ComClose(ComKind);
        }
        this->BringToFront();
        this->Show();
        BtnFirmwareSet->Enabled = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BtnBLEStopClick(TObject *Sender)
{
        BtnBLEStop->Enabled = false;
        AnsiString asSendData = "";
        BYTE SendData[2048];
        int temp = 0;
        int CurMAC = 0;
        int SendSize = 0;
        CurMAC = mMACAddr->ItemIndex;
        AnsiString asSrcMAC = "";
        AnsiString asMac = "";
        AnsiString asMessage = "";
        AnsiString asLog = "";
        UDPClientPort = StrToIntDef(AnsiString(Edit7->Text).TrimRight(), 5108);
        UDPServerPort = StrToIntDef(AnsiString(Edit8->Text).TrimRight(), 5109);
        try{
            asSendData = "++SET++![0042!]";
            SendSize = asSendData.Length();
            memcpy(SendData, asSendData.c_str(), SendSize);
            asMac = "";
            asMac = "255.255.255.255";
            if(cbDirectIP->Checked)
            {
                SIPPort = StrToInt(eDirectPort->Text);
                bool bDDNS = false;
                asMac = cbDirectIPAddr->Text;
                int tempIP = StrToIntDef(asMac.SubString(1, asMac.Pos(".") - 1), -1);
                if(tempIP < 0)
                    bDDNS = true;
                if(!bDDNS)
                {
                    AnsiString asMac2 = "";
                    int temp = 0;
                    AnsiString temp2 = "";
                    int iPos = 0;
                    int iDelPos = 0;
                    char Buf[6];
                    for(int i = 0; i < 4; i++)
                    {
                        if(i == 3)
                            iPos = asMac.Length();
                        else
                            iPos = asMac.Pos(".") - 1;
                        iDelPos = iPos + 1;
                        if(iPos > 3)
                            iPos = 3;
                        try{
                            temp = StrToInt(asMac.SubString(1, iPos));
                        }catch(...){
                            //ShowMessage("Wrong Local IP Address");
                            asMessage = "Wrong Local IP Address";
                            FormMain->Message("\r\n" + asMessage + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                            BtnReset->Enabled = true;
                            return;
                        }
                        ZeroMemory(Buf, sizeof(Buf));
                        wsprintf(Buf, "%03d", temp);
                        asMac = asMac.Delete(1, iDelPos);
                        if(i == 3)
                        {
                            temp2 = temp2 + AnsiString(Buf).SubString(1, 3);
                            asMac2 = asMac2 + IntToStr(temp);
                        }
                        else
                        {
                            temp2 = temp2 + AnsiString(Buf).SubString(1, 3) + ".";
                            asMac2 = asMac2 + IntToStr(temp) + ".";
                        }
                    }
                    //eDirectIPAddr->Text = asMac2;
                    cbDirectIPAddr->Text = asMac2;
                    asMac = asMac2;
                }
                //FormMain->ComClose(FormMain->Setup.ComKind);
                bStopSend = false;
                for(int i = 0; i < SendSize; i++)
                    asLog = asLog + Int2HexConvert(SendData[i]) + " ";
                asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
                if(ComPortSet(1, PortUse1, BaudRateUse1, Disable, Disable, asMac, SIPPort, false))
                {
                    bStopSend = false;
                    ModuleData(ComKind, FormMain->Controler1, SendData, SendSize, 0, AN, false);
                    FormMain->AddLog("TCP/IP Send Data : " + asLog, clBlue);
                    pnComKind->Font->Color = clSilver;
                    ComClose(1);
                }
                else
                    pnComKind->Font->Color = clRed;
            }
            else
            {  
                if(ComKind == 0)
                {
                    SendSize = asSendData.Length();
                    ZeroMemory(SendData, sizeof(SendData));
                    memcpy(SendData, asSendData.c_str(), SendSize);
                    for(int i = 0; i < SendSize; i++)
                        asLog = asLog + Int2HexConvert(SendData[i]) + " ";
                    asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
                    if(ComPortSet(ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, asMac, SIPPort, false))
                    {
                        ComPort1->Write(SendData, SendSize);
                        pnComKind->Font->Color = clSilver;
                        FormMain->AddLog("Serial Send Data : " + asLog, clBlue);
                    }
                    else
                        pnComKind->Font->Color = clRed;
                }
                else
                {
                    for(int i = 0; i < SendSize; i++)
                        asLog = asLog + Int2HexConvert(SendData[i]) + " ";
                    asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
                    IdUDPServer->SendBuffer(asMac, UDPClientPort, SendData, SendSize);
                    if(rbBoth->Checked)
                    {
                        NoneDelayCount(200);
                        IdUDPServer->SendBuffer(asMac, 5107, SendData, SendSize);
                    }
                    pnComKind->Font->Color = clSilver;
                    FormMain->AddLog("UDP Send Data : " + asLog, clBlue);
                }
            }
        }catch(...){
        }
        if(ComKind == 0)
        {
            NoneDelayCount(500);
            ComClose(ComKind);
        }
        this->BringToFront();
        this->Show();
        BtnBLEStop->Enabled = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BtnCloseClick(TObject *Sender)
{
        this->Tag = 1;
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::btnSettingClick(TObject *Sender)
{
        BtnSetting->Enabled = false;
        AnsiString asSendData = "SETT  " + eMACAddr->Text + "  ";
        char Buf[6];
        int iPos = 0;
        AnsiString asMac = eIPAddr->Text;
        AnsiString asMac2 = "";
        int temp = 0;
        AnsiString temp2 = "";
        int iDelPos = 0;
        AnsiString asMessage = "";
        UDPClientPort = StrToIntDef(AnsiString(Edit7->Text).TrimRight(), 5108);
        UDPServerPort = StrToIntDef(AnsiString(Edit8->Text).TrimRight(), 5109);
        for(int i = 0; i < 4; i++)
        {
            if(i == 3)
                iPos = asMac.Length();
            else
                iPos = asMac.Pos(".") - 1;
            iDelPos = iPos + 1;
            if(iPos > 3)
                iPos = 3;
            try{
                temp = StrToInt(asMac.SubString(1, iPos));
            }catch(...){
                //ShowMessage("Wrong Local IP Address");
                asMessage = "Wrong Local IP Address";
                FormMain->Message("\r\n" + asMessage + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                BtnSetting->Enabled = true;
                return;
            }
            ZeroMemory(Buf, sizeof(Buf));
            wsprintf(Buf, "%03d", temp);
            asMac = asMac.Delete(1, iDelPos);
            if(i == 3)
            {
                temp2 = temp2 + AnsiString(Buf).SubString(1, 3);
                asMac2 = asMac2 + IntToStr(temp);
            }
            else
            {
                temp2 = temp2 + AnsiString(Buf).SubString(1, 3) + ".";
                asMac2 = asMac2 + IntToStr(temp) + ".";
            }
        }
        eIPAddr->Text = asMac2;
        asSendData = asSendData + temp2 + "  ";
        asMac = eSubnetMask->Text;
        asMac2 = "";
        temp = 0;
        temp2 = "";
        for(int i = 0; i < 4; i++)
        {
            if(i == 3)
                iPos = asMac.Length();
            else
                iPos = asMac.Pos(".") - 1;
            iDelPos = iPos + 1;
            if(iPos > 3)
                iPos = 3;
            try{
                temp = StrToInt(asMac.SubString(1, iPos));
            }catch(...){
                //ShowMessage("Wrong SubnetMask Address");
                asMessage = "Wrong SubnetMask Address";
                FormMain->Message("\r\n" + asMessage + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                BtnSetting->Enabled = true;
                return;
            }
            ZeroMemory(Buf, sizeof(Buf));
            wsprintf(Buf, "%03d", temp);
            asMac = asMac.Delete(1, iDelPos);
            if(i == 3)
            {
                temp2 = temp2 + AnsiString(Buf).SubString(1, 3);
                asMac2 = asMac2 + IntToStr(temp);
            }
            else
            {
                temp2 = temp2 + AnsiString(Buf).SubString(1, 3) + ".";
                asMac2 = asMac2 + IntToStr(temp) + ".";
            }
        }
        eSubnetMask->Text = asMac2;
        asSendData = asSendData + temp2 + "  ";
        asMac = eGateway->Text;
        asMac2 = "";
        temp = 0;
        temp2 = "";
        for(int i = 0; i < 4; i++)
        {
            if(i == 3)
                iPos = asMac.Length();
            else
                iPos = asMac.Pos(".") - 1;
            iDelPos = iPos + 1;
            if(iPos > 3)
                iPos = 3;
            try{
                temp = StrToInt(asMac.SubString(1, iPos));
            }catch(...){
                //ShowMessage("Wrong Gateway IP Address");
                //btnSetting->Enabled = true;
                //return;
                temp2 = "000.000.000.000";
                asMac2 = "0.0.0.0";
                break;
            }
            ZeroMemory(Buf, sizeof(Buf));
            wsprintf(Buf, "%03d", temp);
            asMac = asMac.Delete(1, iDelPos);
            if(i == 3)
            {
                temp2 = temp2 + AnsiString(Buf).SubString(1, 3);
                asMac2 = asMac2 + IntToStr(temp);
            }
            else
            {
                temp2 = temp2 + AnsiString(Buf).SubString(1, 3) + ".";
                asMac2 = asMac2 + IntToStr(temp) + ".";
            }
        }
        eGateway->Text = asMac2;
        /*if(eIPAddr->Text.SubString(1, eIPAddr->Text.Pos(".")) != eGateway->Text.SubString(1, eIPAddr->Text.Pos(".")))
        {
            ShowMessage("Not Correct between Gateway IP And Local IP Address");
            btnSetting->Enabled = true;
            return;
        }*/
        asSendData = asSendData + temp2 + "  ";
        ZeroMemory(Buf, sizeof(Buf));
        try{
            temp = StrToInt(ePort->Text);
            if(temp > 65535)
            {
                temp = 65535;
                ePort->Text = IntToStr(temp);
            }
        }catch(...){
            //ShowMessage("Wrong Local Port Number");
            asMessage = "Wrong Local Port Number";
            FormMain->Message("\r\n" + asMessage + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
            BtnSetting->Enabled = true;
            return;
        }
        wsprintf(Buf, "%05d", temp);
        asSendData = asSendData + AnsiString(Buf).SubString(1, 5) + "  ";
        //eMACAddr->Text + "  " + eIPAddr->Text + "  " + eSubnetMask->Text +
        //    "  " + eGateway->Text + "  " + ePort->Text + "  ";
        //if(rgComMode->ItemIndex == 1)
        if(rgDHCP->Checked)
            asSendData = asSendData + "31  ";
        else
            asSendData = asSendData + "30  ";
        asMac = eServerIPAddr->Text;
        asMac2 = "";
        temp = 0;
        temp2 = "";
        bool bDDNS = false;
        int tempIP = StrToIntDef(asMac.SubString(1, asMac.Pos(".") - 1), -1);
        if(tempIP < 0)
            bDDNS = true;
        if(bDDNS)
        {
            asMac = "000.000.000.000";
            asSendData = asSendData + asMac + "  ";
        }
        else
        {
            for(int i = 0; i < 4; i++)
            {
                if(i == 3)
                    iPos = asMac.Length();
                else
                    iPos = asMac.Pos(".") - 1;
                iDelPos = iPos + 1;
                if(iPos > 3)
                    iPos = 3;
                try{
                    temp = StrToInt(asMac.SubString(1, iPos));
                }catch(...){
                    //ShowMessage("Wrong Server IP Address");
                    asMessage = "Wrong Server IP Address";
                    FormMain->Message("\r\n" + asMessage + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                    BtnSetting->Enabled = true;
                    return;
                }
                ZeroMemory(Buf, sizeof(Buf));
                wsprintf(Buf, "%03d", temp);
                asMac = asMac.Delete(1, iDelPos);
                if(i == 3)
                {
                    temp2 = temp2 + AnsiString(Buf).SubString(1, 3);
                    asMac2 = asMac2 + IntToStr(temp);
                }
                else
                {
                    temp2 = temp2 + AnsiString(Buf).SubString(1, 3) + ".";
                    asMac2 = asMac2 + IntToStr(temp) + ".";
                }
            }
            eServerIPAddr->Text = asMac2;
            asSendData = asSendData + temp2 + "  ";
        }
        ZeroMemory(Buf, sizeof(Buf));
        try{
            temp = StrToInt(eServerPort->Text);
            if(temp > 65535)
            {
                temp = 65535;
                eServerPort->Text = IntToStr(temp);
            }
        }catch(...){
            //ShowMessage("Wrong Server Port Number");
            asMessage = "Wrong Server Port Number";
            FormMain->Message("\r\n" + asMessage + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
            BtnSetting->Enabled = true;
            return;
        }
        wsprintf(Buf, "%05d", temp);
        int SendSize = 0;
        bAddKind = 2;
        if(bAddKind > 1)
        {
            asSendData = asSendData + AnsiString(Buf).SubString(1, 5) + "  ";
            //if(rgOperationMode->ItemIndex == 0)
            if(rbServer->Checked)
                asSendData = asSendData + "31  ";
            else
                asSendData = asSendData + "30  ";
            asMac = eMemo->Text.SubString(1, 20);
            SendSize = asMac.Length();
            if(SendSize < 20)
            {
                for(int i = SendSize; i < 20; i++)
                    asMac = asMac.Insert(" ", asMac.Length() + 1);
            }
            asSendData = asSendData + asMac + "  ";
            try{
                temp = StrToInt(eInterval->Text);
            }catch(...){
                //ShowMessage("Wrong Server Port Number");
                asMessage = "Wrong Server Port Number";
                FormMain->Message("\r\n" + asMessage + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                BtnSetting->Enabled = true;
                return;
            }
            wsprintf(Buf, "%03d", temp);
            asSendData = asSendData + AnsiString(Buf).SubString(1, 3) + "  ";
            if(bDDNS)
            {
                asMac = eServerIPAddr->Text.SubString(1, 30);
                SendSize = asMac.Length();
                if(SendSize < 30)
                {
                    for(int i = SendSize; i < 30; i++)
                        asMac = asMac.Insert(" ", asMac.Length() + 1);
                }
            }
            else
            {
                asMac = "";
                for(int i = 0; i < 30; i++)
                    asMac = asMac + " ";
            }
            asSendData = asSendData + asMac + "  ";
            if(rbWiFiSet->Checked || cbWiFiSet->Checked)
            {
                asMac = eSSID->Text.SubString(1, 20);
                SendSize = asMac.Length();
                if(SendSize < 20)
                {
                    for(int i = SendSize; i < 20; i++)
                        asMac = asMac.Insert(" ", asMac.Length() + 1);
                }
                asSendData = asSendData + asMac + "  ";

                asMac = eSSIDPW->Text.SubString(1, 20);
                SendSize = asMac.Length();
                if(SendSize < 20)
                {
                    for(int i = SendSize; i < 20; i++)
                        asMac = asMac.Insert(" ", asMac.Length() + 1);
                }
                asSendData = asSendData + asMac + "  ";
            }
            else
            {
                for(int i = 0; i < 44; i++)
                    asSendData = asSendData + " ";
            }
            if(rbEth->Checked)
                asSendData = asSendData + "00";
            else if(rbSTA->Checked)
                asSendData = asSendData + "30";
            else
                asSendData = asSendData + "31";
            /*if(rbBluetooth->Checked)
                asSendData = asSendData + "34";
            else if(rbWiFi->Checked)
                asSendData = asSendData + "35";
            else
                asSendData = asSendData + "33";
            if(rbRS232->Checked)
                asSendData = asSendData + "32";
            else
                asSendData = asSendData + "31";*/
            if(ComKind == 0)
                asSendData = "++SET++![" + asSendData + "\r\n!]";
            else
                asSendData = asSendData + "\r\n";
        }
        else
        {
            if(ComKind == 0)
                asSendData = "++SET++![" + asSendData + AnsiString(Buf).SubString(1, 5) + "\r\n!]";
            else
                asSendData = asSendData + AnsiString(Buf).SubString(1, 5) + "\r\n";
        }
        BYTE SendData[2048];
        SendSize = asSendData.Length();
        ZeroMemory(SendData, sizeof(SendData));
        memcpy(SendData, asSendData.c_str(), SendSize);
        asMac = "255.255.255.255";
        AnsiString asLog = "";
        for(int i = 0; i < SendSize; i++)
            asLog = asLog + Int2HexConvert(SendData[i]) + " ";
        asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
        try{
            if(cbDirectIP->Checked)
            {
                //asMac = eDirectIPAddr->Text;
                SIPPort = StrToInt(eDirectPort->Text);
                bDDNS = false;
                asMac = cbDirectIPAddr->Text;
                tempIP = StrToIntDef(asMac.SubString(1, asMac.Pos(".") - 1), -1);
                if(tempIP < 0)
                    bDDNS = true;
                if(!bDDNS)
                {
                    asMac2 = "";
                    temp = 0;
                    temp2 = "";
                    for(int i = 0; i < 4; i++)
                    {
                        if(i == 3)
                            iPos = asMac.Length();
                        else
                            iPos = asMac.Pos(".") - 1;
                        iDelPos = iPos + 1;
                        if(iPos > 3)
                            iPos = 3;
                        try{
                            temp = StrToInt(asMac.SubString(1, iPos));
                        }catch(...){
                            //ShowMessage("Wrong Local IP Address");
                            asMessage = "Wrong Local IP Address";
                            FormMain->Message("\r\n" + asMessage + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                            BtnSetting->Enabled = true;
                            return;
                        }
                        ZeroMemory(Buf, sizeof(Buf));
                        wsprintf(Buf, "%03d", temp);
                        asMac = asMac.Delete(1, iDelPos);
                        if(i == 3)
                        {
                            temp2 = temp2 + AnsiString(Buf).SubString(1, 3);
                            asMac2 = asMac2 + IntToStr(temp);
                        }
                        else
                        {
                            temp2 = temp2 + AnsiString(Buf).SubString(1, 3) + ".";
                            asMac2 = asMac2 + IntToStr(temp) + ".";
                        }
                    }
                    //eDirectIPAddr->Text = asMac2;
                    cbDirectIPAddr->Text = asMac2;
                    asMac = asMac2;
                }
                if(ComPortSet(1, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, asMac, SIPPort, false))
                {
                    bStopSend = false;
                    pnComKind->Font->Color = clSilver;
                    TransferData(1, FormMain->Controler1, SendData, SendSize, 0, AN);
                    FormMain->AddLog("TCP/IP Send Data : " + asLog, clBlue);
                    ComClose(1);
                }
                else
                    pnComKind->Font->Color = clRed;
            }
            else
            {
                if(ComKind == 0)
                {
                    if(ComPortSet(ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, asMac, SIPPort, false))
                    {
                        pnComKind->Font->Color = clSilver;
                        ComPort1->Write(SendData, SendSize);
                        FormMain->AddLog("Serial Send Data : " + asLog, clBlue);
                    }
                    else
                       pnComKind->Font->Color = clRed;
                }
                else
                {
                    IdUDPServer->SendBuffer(asMac, UDPClientPort, SendData, SendSize);
                    if(rbBoth->Checked)
                    {
                        NoneDelayCount(100);
                        IdUDPServer->SendBuffer(asMac, 5107, SendData, SendSize);
                    }
                    FormMain->AddLog("UDP Send Data : " + asLog, clBlue);
                    pnComKind->Font->Color = clSilver;
                }
            }
        }catch(...){
        }
        mMACAddr->Clear();
        /*eMemo->Text = "";
        eIPAddr->Text = "";
        eSubnetMask->Text = "";
        eGateway->Text = "";
        ePort->Text = "";
        rgStatic->Checked = false;
        rgDHCP->Checked = false;
        eServerIPAddr->Text = "";
        eServerPort->Text = "";
        eInterval->Text = "";
        eServerName->Text = "";
        //rgOperationMode->ItemIndex = -1;
        rbServer->Checked = false;
        rbClient->Checked = false;
        eSSID->Text = "";
        eSSIDPW->Text = "";
        //cbAPMode->ItemIndex = -1;
        rbSTA->Checked = false;
        rbAP->Checked = false;
        rbEth->Checked = false;
        //cbWiFiMode->ItemIndex = -1;
        rbBluetooth->Checked = false;
        rbWiFi->Checked = false;
        rbEthernetSet->Checked = false;
        rbWiFiSet->Checked = false;
        cbWiFiSet->Checked = false;*/
        MACCnt = 0;
        if(ComKind == 0)
        {
            NoneDelayCount(1000);
            ComClose(ComKind);
        }
        this->BringToFront();
        this->Show();
        BtnSetting->Enabled = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BtnAddClick(TObject *Sender)
{
        //if(FormMain->ComKind == 3)
        //    FormMain->UDPIPAddress = AnsiString(eIPAddr->Text).TrimRight();
        //else
        //{
        //    FormMain->IPAddress = AnsiString(eIPAddr->Text).TrimRight();
        //    FormMain->IPPort = StrToInt(AnsiString(ePort->Text).TrimRight());
        //}
        this->Tag = 2;
        Close();
}
//---------------------------------------------------------------------------
                                                       
void __fastcall TFormDABITNet::mMACAddrClick(TObject *Sender)
{
        mMACAddr->Invalidate();
        AnsiString asSrcMAC = MACIPData[mMACAddr->ItemIndex];
        AnsiString asMac = asSrcMAC.SubString(7, 17);
        AnsiString asMac2 = "";
        AnsiString temp = "";
        eMACAddr->Text = asMac;
        asMac = asSrcMAC.SubString(26, 15);
        int iPos = 0;
        for(int i = 0; i < 4; i++)
        {
            if(i == 3)
                iPos = asMac.Length();
            else
                iPos = asMac.Pos(".") - 1;
            temp = IntToStr(StrToIntDef(asMac.SubString(1, iPos), 0));
            asMac = asMac.Delete(1, iPos + 1);
            if(i == 3)
                asMac2 = asMac2 + temp;
            else
                asMac2 = asMac2 + temp + ".";
        }
        eIPAddr->Text = asMac2;
        asMac2 = "";
        asMac = asSrcMAC.SubString(43, 15);
        for(int i = 0; i < 4; i++)
        {
            if(i == 3)
                iPos = asMac.Length();
            else
                iPos = asMac.Pos(".") - 1;
            temp = IntToStr(StrToIntDef(asMac.SubString(1, iPos), 0));
            asMac = asMac.Delete(1, iPos + 1);
            if(i == 3)
                asMac2 = asMac2 + temp;
            else
                asMac2 = asMac2 + temp + ".";
        }
        eSubnetMask->Text = asMac2;
        asMac2 = "";
        asMac = asSrcMAC.SubString(60, 15);
        for(int i = 0; i < 4; i++)
        {
            if(i == 3)
                iPos = asMac.Length();
            else
                iPos = asMac.Pos(".") - 1;
            temp = IntToStr(StrToIntDef(asMac.SubString(1, iPos), 0));
            asMac = asMac.Delete(1, iPos + 1);
            if(i == 3)
                asMac2 = asMac2 + temp;
            else
                asMac2 = asMac2 + temp + ".";
        }
        eGateway->Text = asMac2;
        asMac = asSrcMAC.SubString(77, 5);
        asMac2 = IntToStr(StrToIntDef(asMac, 0));
        ePort->Text = asMac2;
        asMac = asSrcMAC.SubString(84, 2);
        //rgComMode->ItemIndex = -1;
        if(asMac == "31")
            rgDHCP->Checked = true;
        else
            rgStatic->Checked = true;
        asMac = asSrcMAC.SubString(88, 15);
        asMac2 = "";
        for(int i = 0; i < 4; i++)
        {
            if(i == 3)
                iPos = asMac.Length();
            else
                iPos = asMac.Pos(".") - 1;
            temp = IntToStr(StrToIntDef(asMac.SubString(1, iPos), 0));
            asMac = asMac.Delete(1, iPos + 1);
            if(i == 3)
                asMac2 = asMac2 + temp;
            else
                asMac2 = asMac2 + temp + ".";
        }
        eServerIPAddr->Text = asMac2;
        asMac = asSrcMAC.SubString(105, 5);
        asMac2 = IntToStr(StrToIntDef(asMac, 0));
        eServerPort->Text = asMac2;
        if(bAddKind > 1)
        {
            asMac = asSrcMAC.SubString(112, 2);
            //rgOperationMode->ItemIndex = -1;
            if(asMac == "30")
                rbClient->Checked = true;
            else
                rbServer->Checked = true;
            asMac = asSrcMAC.SubString(116, 20);
            eMemo->Text = asMac.TrimRight();
            asMac = asSrcMAC.SubString(138, 3);
            asMac2 = IntToStr(StrToIntDef(asMac, 0));
            eInterval->Text = asMac2;
            asMac = asSrcMAC.SubString(143, 30).TrimRight();
            if(asMac != "" && asMac != " ")
                eServerIPAddr->Text = asMac.TrimRight();
            //if(asMac != "" && asMac != " ")
            //    rbServerName->Checked = true;
            //else
            //    rbServerIP->Checked = true;
            eServerName->Text = asMac.TrimRight();

            asMac = asSrcMAC.SubString(175, 20).TrimRight();
            if(asMac == "")
            {
                rbEthernetSet->Checked = true;
                cbWiFiSet->Checked = false;
            }
            else
            {
                rbWiFiSet->Checked = true;
                cbWiFiSet->Checked = true;
            }
            eSSID->Text = asMac.TrimRight();

            asMac = asSrcMAC.SubString(197, 20).TrimRight();
            eSSIDPW->Text = asMac.TrimRight();

            asMac = asSrcMAC.SubString(219, 2);
            //cbAPMode->ItemIndex = -1;
            if(asMac == "00")
                rbEth->Checked = true;
            else if(asMac == "31")
                rbAP->Checked = true;
            else
                rbSTA->Checked = true;

            /*asMac = asSrcMAC.SubString(223, 2);
            //cbWiFiMode->ItemIndex = -1;
            rbWiFi->Checked = false;
            if(asMac == "34")
                rbBluetooth->Checked = true;
            else if(asMac == "35")
                rbWiFi->Checked = true;
            else
                rbEthernet->Checked = true;

            asMac = asSrcMAC.SubString(225, 2);
            if(asMac == "32")
                rbRS232->Checked = true;
            else
                rbTTL->Checked = true;*/
        }
        iPos = 0;
        asMac = MACIPData[mMACAddr->ItemIndex].SubString(7, 17);
        laMACAddr->Caption = asMac;
        for(int i = 0; i < asMac.Length(); i++)
        {
            iPos = asMac.Pos("-");
            if(iPos)
                asMac = asMac.Delete(iPos, 1);
            else
                break;
        }
        etMACAddr->Text = asMac.UpperCase();
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::ApplicationEvents1ShortCut(TWMKey &Msg,
      bool &Handled)
{
        switch(Msg.CharCode)
        {
            case VK_F9 :
            {
                etMACAddr->Text = (WideString)"";
                etMACAddr->Visible = !etMACAddr->Visible;
            }
            break;
            case VK_F10 :
            {
                pnBtnBLEStop->Visible = !pnBtnBLEStop->Visible;
            }
            break;
            case VK_F11 :
            {
                //rgComMode->Visible = !rgComMode->Visible;
                laServerIP->Visible = !laServerIP->Visible;
                eServerIPAddr->Visible = !eServerIPAddr->Visible;
                TntLabel8->Visible = !TntLabel8->Visible;
                eServerPort->Visible = !eServerPort->Visible;
                laServerDDNS->Visible = !laServerDDNS->Visible;
                eServerName->Visible = !eServerName->Visible;
            }
            break;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::rgComModeClick(TObject *Sender)
{
        eIPAddr->Enabled = !rgDHCP->Checked;
        eSubnetMask->Enabled = !rgDHCP->Checked;
        eGateway->Enabled = !rgDHCP->Checked;
        ePort->Enabled = !rgDHCP->Checked;
        TntLabel3->Enabled = !rgDHCP->Checked;
        TntLabel4->Enabled = !rgDHCP->Checked;
        TntLabel5->Enabled = !rgDHCP->Checked;
        TntLabel7->Enabled = !rgDHCP->Checked;
        /*if(rgStatic->Checked)
        {
            eIPAddr->Color = clWindow;
            eSubnetMask->Color = clWindow;
            eGateway->Color = clWindow;
            ePort->Color = clWindow;
        }
        else
        {
            eIPAddr->Color = clWindowFrame;
            eSubnetMask->Color = clWindowFrame;
            eGateway->Color = clWindowFrame;
            ePort->Color = clWindowFrame;
        }*/
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::mMACAddrDrawItem(TWinControl *Control,
      int Index, TRect &Rect, TOwnerDrawState State)
{
        int     Offset = 2;
        TCanvas *pCanvas = ((TTntListBox *)Control)->Canvas;

        pCanvas->FillRect(Rect);
        if(Index == mMACAddr->ItemIndex)
        {
            pCanvas->Font->Color = clRed;
            pCanvas->Font->Style = TFontStyles() << fsBold;
        }
        else
        {
            pCanvas->Font->Color = clBlack;
            pCanvas->Font->Style = TFontStyles() >> fsBold;
        }
        pCanvas->TextOut(Rect.Left + Offset, Rect.Top, ((TTntListBox *)Control)->Items->Strings[Index]);
        /*Graphics::TBitmap *pBitmap=new Graphics::TBitmap;
        int     Offset = 2;
        TCanvas *pCanvas = ((TListBox *)Control)->Canvas;

        pCanvas->FillRect(Rect);
        pBitmap = (Graphics::TBitmap *)((TListBox *)Control)->Items->Objects[Index];

        if (pBitmap)
        {
          pCanvas->BrushCopy(Bounds(Rect.Left + Offset, Rect.Top, pBitmap->Width, pBitmap->Height),
             pBitmap, Bounds(0, 0, pBitmap->Width, pBitmap->Height), clRed);
          Offset += pBitmap->Width + 4;
        }
        pCanvas->Font->Color = clRed;
        pCanvas->TextOut( Rect.Left + Offset, Rect.Top, ((TListBox *)Control)->Items->Strings[Index]);*/
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::eMemoKeyPress(TObject *Sender, char &Key)
{
        int TextS = eMemo->GetTextLen();
        if(TextS >= 20)
            SendMessage(eMemo->Handle, EM_LIMITTEXT, 10,0);
        else
            SendMessage(eMemo->Handle, EM_LIMITTEXT, 20,0);
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BtnResetClick(TObject *Sender)
{
        BtnReset->Enabled = false;
        AnsiString asSendData = "RESET  " + eMACAddr->Text + "\r\n";
        BYTE SendData[2048];
        int TotMACnt = 0;
        int CurMAC = 0;
        int SendSize = asSendData.Length();
        if(Sender == BtnAllReset)
            TotMACnt = MACCnt;
        else
        {
            CurMAC = mMACAddr->ItemIndex;
            TotMACnt = mMACAddr->ItemIndex + 1;
        }
        AnsiString asSrcMAC = "";
        AnsiString asMac = "";
        AnsiString asMessage = "";
        AnsiString asLog = "";
        UDPClientPort = StrToIntDef(AnsiString(Edit7->Text).TrimRight(), 5108);
        UDPServerPort = StrToIntDef(AnsiString(Edit8->Text).TrimRight(), 5109);
        try{
            for(int i = CurMAC; i < TotMACnt; i++)
            {
                asSrcMAC = MACIPData[i];
                asMac = asSrcMAC.SubString(7, 17);
                asSendData = "RESET  " + asMac;
                ZeroMemory(SendData, sizeof(SendData));
                memcpy(SendData, asSendData.c_str(), SendSize);
                AnsiString asMac = "";
                asMac = "255.255.255.255";
                if(cbDirectIP->Checked)
                {
                    SIPPort = StrToInt(eDirectPort->Text);
                    bool bDDNS = false;
                    asMac = cbDirectIPAddr->Text;
                    int tempIP = StrToIntDef(asMac.SubString(1, asMac.Pos(".") - 1), -1);
                    if(tempIP < 0)
                        bDDNS = true;
                    if(!bDDNS)
                    {
                        AnsiString asMac2 = "";
                        int temp = 0;
                        AnsiString temp2 = "";
                        int iPos = 0;
                        int iDelPos = 0;
                        char Buf[6];
                        for(int i = 0; i < 4; i++)
                        {
                            if(i == 3)
                                iPos = asMac.Length();
                            else
                                iPos = asMac.Pos(".") - 1;
                            iDelPos = iPos + 1;
                            if(iPos > 3)
                                iPos = 3;
                            try{
                                temp = StrToInt(asMac.SubString(1, iPos));
                            }catch(...){
                                //ShowMessage("Wrong Local IP Address");
                                asMessage = "Wrong Local IP Address";
                                FormMain->Message("\r\n" + asMessage + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                                BtnReset->Enabled = true;
                                return;
                            }
                            ZeroMemory(Buf, sizeof(Buf));
                            wsprintf(Buf, "%03d", temp);
                            asMac = asMac.Delete(1, iDelPos);
                            if(i == 3)
                            {
                                temp2 = temp2 + AnsiString(Buf).SubString(1, 3);
                                asMac2 = asMac2 + IntToStr(temp);
                            }
                            else
                            {
                                temp2 = temp2 + AnsiString(Buf).SubString(1, 3) + ".";
                                asMac2 = asMac2 + IntToStr(temp) + ".";
                            }
                        }
                        //eDirectIPAddr->Text = asMac2;
                        cbDirectIPAddr->Text = asMac2;
                        asMac = asMac2;
                    }
                    //FormMain->ComClose(FormMain->Setup.ComKind);
                    bStopSend = false;
                    for(int i = 0; i < SendSize; i++)
                        asLog = asLog + Int2HexConvert(SendData[i]) + " ";
                    asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
                    if(ComPortSet(1, PortUse1, BaudRateUse1, Disable, Disable, asMac, SIPPort, false))
                    {
                        bStopSend = false;
                        pnComKind->Font->Color = clSilver;
                        ModuleData(ComKind, FormMain->Controler1, SendData, SendSize, 0, AN, false);
                        FormMain->AddLog("TCP/IP Send Data : " + asLog, clBlue);
                        ComClose(1);
                    }
                    else
                        pnComKind->Font->Color = clRed;
                }
                else
                {
                    if(ComKind == 0)
                    {
                        asSendData = "++SET++![" + asSendData + "!]";
                        SendSize = asSendData.Length();
                        ZeroMemory(SendData, sizeof(SendData));
                        memcpy(SendData, asSendData.c_str(), SendSize);
                        for(int i = 0; i < SendSize; i++)
                            asLog = asLog + Int2HexConvert(SendData[i]) + " ";
                        asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
                        if(ComPortSet(ComKind, FormMain->PortUse1, FormMain->BaudRateUse1, Disable, Disable, asMac, SIPPort, false))
                        {
                            pnComKind->Font->Color = clSilver;
                            ComPort1->Write(SendData, SendSize);
                            FormMain->AddLog("Serial Send Data : " + asLog, clBlue);
                        }
                        else
                           pnComKind->Font->Color = clRed;
                    }
                    else
                    {
                        for(int i = 0; i < SendSize; i++)
                            asLog = asLog + Int2HexConvert(SendData[i]) + " ";
                        asLog = asLog + "\r\n" + AnsiString((char *)SendData).SubString(1, SendSize);
                        IdUDPServer->SendBuffer(asMac, UDPClientPort, SendData, SendSize);
                        if(rbBoth->Checked)
                        {
                            NoneDelayCount(100);
                            IdUDPServer->SendBuffer(asMac, 5107, SendData, SendSize);
                        }
                        pnComKind->Font->Color = clSilver;
                        FormMain->AddLog("UDP Send Data : " + asLog, clBlue);
                    }
                }
            }
        }catch(...){
        }
        if(ComKind == 0)
        {
            NoneDelayCount(1000);
            ComClose(ComKind);
        }
        this->BringToFront();
        this->Show();
        BtnReset->Enabled = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::rgOperationModeClick(TObject *Sender)
{
        //if(rgOperationMode->ItemIndex == 1)
        if(rbClient->Checked)
        {
            rbServerIP->Enabled = true;
            rbServerName->Enabled = true;
            /*if(rbServerName->Checked)
            {
                laServerIP->Enabled = false;
                eServerIPAddr->Enabled = false;
                eServerName->Enabled = true;
                laServerDDNS->Enabled = true;
            }
            else
            {
                laServerIP->Enabled = true;
                eServerIPAddr->Enabled = true;
                eServerName->Enabled = false;
                laServerDDNS->Enabled = false;
            }*/
            laServerIP->Enabled = true;
            eServerIPAddr->Enabled = true;
            //eServerIPAddr->Color = clWindow;
            eServerName->Enabled = true;
            //eServerName->Color = clWindow;
            laServerDDNS->Enabled = true;
            TntLabel8->Enabled = true;
            eServerPort->Enabled = true;
            //eServerPort->Color = clWindow;
            TntLabel8->Enabled = true;
            eServerPort->Enabled = true;
        }
        else
        {
            rbServerIP->Enabled = false;
            laServerIP->Enabled = false;
            eServerIPAddr->Enabled = false;
            //eServerIPAddr->Color = clWindowFrame;
            rbServerName->Enabled = false;
            eServerName->Enabled = false;
            //eServerName->Color = clWindowFrame;
            laServerDDNS->Enabled = false;
            TntLabel8->Enabled = false;
            eServerPort->Enabled = false;
            //eServerPort->Color = clWindowFrame;
        }
}
//---------------------------------------------------------------------------
                                           
void __fastcall TFormDABITNet::cbDirectIPClick(TObject *Sender)
{
        cbDirectIPAddr->Visible = cbDirectIP->Checked;
        TntLabel10->Visible = cbDirectIP->Checked;
        eDirectPort->Visible = cbDirectIP->Checked;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::RoadIniFile()
{
        AnsiString IniFileName = ChangeFileExt(Application->ExeName, ".ini");
        AnsiString SectionName;

        try
        {
            if(!FileExists(IniFileName))
            {
                TFileStream *fs = NULL;
                fs = new TFileStream(IniFileName, fmCreate | fmShareCompat);
                delete fs;
            }
        }
        catch(...)
        {
        }

        TIniFile * IF = NULL;
        try
        {
            IF = new TIniFile(IniFileName);
            SectionName = "DABITNet";
            ComKind                  = IF->ReadInteger(SectionName, "ComKind", 1);
            //PortUse1                 = IF->ReadString(SectionName, "PortUse1", "COM1");
            //BaudRateUse1             = (eBaudRate)IF->ReadInteger(SectionName, "BaudRate1", 6);
            //DTR1                     = (eFlow)IF->ReadInteger(SectionName, "DTR1", Disable);
            //RTS1                     = (eFlow)IF->ReadInteger(SectionName, "RTS1", Disable);
            UDPClientPort            = IF->ReadInteger(SectionName, "DST_PORT", 5108);
            UDPServerPort            = 5109;//IF->ReadInteger(SectionName, "SRC_PORT", 5109);
            //SIPAddress               = IF->ReadString(SectionName, "IPAddress", "127.0.0.1");
            SIPPort                  = FormMain->IPPort;//IF->ReadInteger(SectionName, "IPPort", 5000);
            //WaitTime                 = IF->ReadInteger(SectionName, "ReplyPacket0_DelayTime", 3000);
            //DelayTime                = IF->ReadInteger(SectionName, "ReplyPacket1_DelayTime", 350);
            //ServerCommOpenDelay      = IF->ReadInteger(SectionName, "SvrCommOpenDly", 3000);
            //Controler1               = IF->ReadInteger(SectionName, "DABITAddress", 0);
            //bFlagReplyPacket         = IF->ReadBool(SectionName, "FlagReplyPacket", false);
            //bFlagCrcPacket           = IF->ReadBool(SectionName, "CrcCheck", false);
            //bFlagDlePacket           = IF->ReadBool(SectionName, "FlagDlePacket", false);
            DirectIPCnt              = IF->ReadInteger(SectionName, "DirectIPCnt", 0);
            DirectIPIndex            = IF->ReadInteger(SectionName, "DirectIPIndex", 0);
            DabitNetTap              = IF->ReadInteger(SectionName, "DabitNetTap", 0);
            UDPType                  = IF->ReadInteger(SectionName, "UDPType", 0);
            LastMac                  = IF->ReadString(SectionName, "LastMac", "");
            UDPUnicast               = IF->ReadBool(SectionName, "UDPUnicast", false);
            DB300Visible             = IF->ReadBool(SectionName, "DB300Visible", false);
            DefaultName              = IF->ReadString(SectionName, "DefaultName", "DB300");
            DefaultIP                = IF->ReadString(SectionName, "DefaultIP", "192.168.0.201");
            DefaultPort              = IF->ReadInteger(SectionName, "DefaultPort", 5000);
            DefaultSubnetMask        = IF->ReadString(SectionName, "DefaultSubnetMask", "255.255.255.0");
            DefaultGateway           = IF->ReadString(SectionName, "DefaultGateway", "192.168.0.1");
            DefaultServerIP          = IF->ReadString(SectionName, "DefaultServerIP", "192.168.0.25");
            DefaultServerPort        = IF->ReadInteger(SectionName, "DefaultServerPort", 5000);
        }__finally{
            if(IF)
            {
                delete IF;
                IF = NULL;
            }
        }
        AnsiString DirectIPFileName = FormMain->BasePath + IncludeTrailingPathDelimiter(FormMain->DestSysPath) + "DirectIPData.dat";
        TFileStream *DirectIPFile = NULL;
        char cSaveIPData[10][100];
        ZeroMemory(cSaveIPData, sizeof(cSaveIPData));
        if(FileExists(DirectIPFileName))
        {
            try{
                DirectIPFile = new TFileStream(DirectIPFileName, fmOpenRead | fmShareCompat);
                DirectIPFile->Read(&cSaveIPData, sizeof(cSaveIPData));
                for(int i = 0; i < 10; i++)
                    asDirectIPAddress[i] = AnsiString((char*)cSaveIPData[i]).SubString(1, 100);
            }catch(...){
            }
            if(DirectIPFile)
            {
                delete DirectIPFile;
                DirectIPFile = NULL;
            }
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::SaveIniFile()
{
        AnsiString IniFileName = ChangeFileExt(Application->ExeName, ".ini");
        AnsiString SectionName;

        try
        {
            if(!FileExists(IniFileName))
            {
                TFileStream *fs = NULL;
                fs = new TFileStream(IniFileName, fmCreate | fmShareCompat);
                delete fs;
            }
        }
        catch(...)
        {
        }

        TIniFile * IF = NULL;
        try
        {
            IF = new TIniFile(IniFileName);
            SectionName = "DABITNet";
            IF->WriteInteger(SectionName, "ComKind", ComKind);
            //IF->WriteString(SectionName, "PortUse1", PortUse1);
            //IF->WriteInteger(SectionName, "BaudRate1", BaudRateUse1);
            //IF->WriteInteger(SectionName, "DTR1", DTR1);
            //IF->WriteInteger(SectionName, "RTS1", RTS1);
            //IF->WriteInteger(SectionName, "DST_PORT", UDPClientPort);
            //IF->WriteInteger(SectionName, "SRC_PORT", UDPServerPort);
            //IF->WriteString(SectionName, "IPAddress", SIPAddress);
            FormMain->IPPort = SIPPort;//IF->WriteInteger(SectionName, "IPPort", SIPPort);
            //IF->WriteBool(SectionName, "FlagReplyPacket", DelayRun);
            //IF->WriteInteger(SectionName, "ReplyPacket0_DelayTime", WaitTime);
            //IF->WriteInteger(SectionName, "ReplyPacket1_DelayTime", DelayTime);
            //IF->WriteInteger(SectionName, "SvrCommOpenDly", ServerCommOpenDelay);
            //IF->WriteInteger(SectionName, "DABITAddress", Controler1);
            //IF->WriteBool(SectionName, "FlagReplyPacket", bFlagReplyPacket);
            //IF->WriteBool(SectionName, "FlagCrcPacket", bFlagCrcPacket);
            //IF->WriteBool(SectionName, "FlagDlePacket", bFlagDlePacket);
            IF->WriteInteger(SectionName, "DirectIPCnt", DirectIPCnt);
            IF->WriteInteger(SectionName, "DirectIPIndex", DirectIPIndex);
            IF->WriteInteger(SectionName, "DabitNetTap", DabitNetTap);
            IF->WriteInteger(SectionName, "UDPType", UDPType);
            IF->WriteString(SectionName, "LastMac", LastMac);
            IF->WriteBool(SectionName, "UDPUnicast", UDPUnicast);
            IF->WriteBool(SectionName, "DB300Visible", DB300Visible);
            IF->WriteString(SectionName, "DefaultName", DefaultName);
            IF->WriteString(SectionName, "DefaultIP", DefaultIP);
            IF->WriteInteger(SectionName, "DefaultPort", DefaultPort);
            IF->WriteString(SectionName, "DefaultSubnetMask", DefaultSubnetMask);
            IF->WriteString(SectionName, "DefaultGateway", DefaultGateway);
            IF->WriteString(SectionName, "DefaultServerIP", DefaultServerIP);
            IF->WriteInteger(SectionName, "DefaultServerPort", DefaultServerPort);
        }__finally{
            if(IF)
            {
                delete IF;
                IF = NULL;
            }
        }
        AnsiString DirectIPFileName = FormMain->BasePath + IncludeTrailingPathDelimiter(FormMain->DestSysPath) + "DirectIPData.dat";
        TFileStream *DirectIPFile = NULL;
        char cSaveIPData[10][100];
        ZeroMemory(cSaveIPData, sizeof(cSaveIPData));
        for(int i = 0; i < 10; i++)
            memcpy(&cSaveIPData[i], asDirectIPAddress[i].c_str(), asDirectIPAddress[i].Length());
        try{
            DirectIPFile = new TFileStream(DirectIPFileName, fmCreate | fmShareCompat);
            DirectIPFile->Write(cSaveIPData, sizeof(cSaveIPData));
        }catch(...){
        }
        if(DirectIPFile)
        {
            delete DirectIPFile;
            DirectIPFile = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::FormCloseQuery(TObject *Sender, bool &CanClose)
{
        SIPPort = StrToIntDef(eDirectPort->Text, 5000);
        DirectIPIndex = cbDirectIPAddr->ItemIndex;
        LastMac = laMACAddr->Caption;
        SaveIniFile();
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::IdIcmpClient1Reply(TComponent *ASender,
      const TReplyStatus &AReplyStatus)
{
        DNSAddress = AReplyStatus.FromIpAddress;
        DNSConnect = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::ClientSocket1Connect(TObject *Sender,
      TCustomWinSocket *Socket)
{
        DNSConnect = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::ClientSocket1Disconnect(TObject *Sender,
      TCustomWinSocket *Socket)
{
        bDisConnect = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::ClientSocket1Error(TObject *Sender,
      TCustomWinSocket *Socket, TErrorEvent ErrorEvent, int &ErrorCode)
{
        ErrorCode = 0;
        switch (ErrorEvent)
        {
            case eeGeneral, eeSend, eeReceive, eeConnect, eeDisconnect, eeAccept, eeLookup:
                bDisConnect = true;
            break;
        }
        ClientSocket1->Socket->Disconnect(ClientSocket1->Socket->SocketHandle);
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::ClientSocket1Read(TObject *Sender,
      TCustomWinSocket *Socket)
{
        int Size = Socket->ReceiveLength();
        char *Buffer = new char[Size];
        ZeroMemory(Buffer, Size);
        Socket->ReceiveBuf(Buffer, Size);
        RecieveData(Buffer, Size);
        //ReadParsing(1, 0, Buffer, Size);
        if(Buffer)
        {
            delete [] Buffer;
            Buffer = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::SerialDelayCount(int Cnt)
{
        DWORD nowTick = GetTickCount();
        DWORD targetTick = nowTick + Cnt;
        while(nowTick < targetTick)
        {
            nowTick = GetTickCount();
            Application->ProcessMessages();
            if(!bSerial)
                break;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::NoneDelayCount(int Cnt)
{
        DWORD nowTick = GetTickCount();
        DWORD targetTick = nowTick + Cnt;
        while(nowTick < targetTick)
        {
            nowTick = GetTickCount();
            Application->ProcessMessages();
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::LANDelayCount(int Cnt)
{
        /*for(int i = 0; i < Cnt; i++)
        {
            Sleep(10);
            Application->ProcessMessages();
            if(DNSConnect)
                break;
        }*/
        DWORD nowTick = GetTickCount();
        DWORD targetTick = nowTick + Cnt;
        DWORD gap;
        bool bShow = false;
        //TFormMessage *FMessage = NULL;
        while(nowTick < targetTick)
        {
            nowTick = GetTickCount();
            Application->ProcessMessages();
            /*if(FGolfDataTime)
            {
                //if(Setup.ReceiveAuto)
                //{
                    gap = (targetTick - nowTick - 1) / 1000;
                    FGolfDataTime->Label3->Caption = "0 (" + IntToStr(gap + 1) + ")";
                //}
            }*/
            gap = (targetTick - nowTick - 1) / 1000;
            if(this->Showing)
            {
                if((Cnt / 1000) - gap > 4 && !bShow)
                {
                    bShow = true;
                    /*FMessage = new TFormMessage(this);
                    FMessage->Caption = "Message";
                    FMessage->laMessage->Font->Color = clRed;
                    FMessage->laMessage->Font->Style = TFontStyles() << fsBold;
                    FMessage->laMessage->Caption = "\r\nConnecting Network. Please wait ...\r\n";
                    //FMessage->TeForm1->Constraints->MinWidth = (16 * 12) * 2;
                    FMessage->Width = (17 * 12) * 2;//FMessage->TeForm1->Constraints->MinWidth;
                    //FMessage->TeForm1->Constraints->MaxWidth = FMessage->TeForm1->Constraints->MinWidth;
                    FMessage->laMessage->Width = FMessage->Width;
                    FMessage->laMessage->Top = 18;
                    FMessage->laMessage->Height = FMessage->laMessage->Top + 4 * 13;
                    FMessage->Height = 90 + FMessage->laMessage->Height + FMessage->btnCancel->Height;
                    FMessage->btnYes->Top = FMessage->laMessage->Top + FMessage->laMessage->Height + 12;
                    FMessage->laMessage->Alignment = (TAlignment)2;
                    FMessage->btnYes->Visible = false;
                    FMessage->btnNo->Visible = false;
                    FMessage->btnCancel->Caption = "Cancel";
                    FMessage->btnCancel->Top = FMessage->laMessage->Top + FMessage->laMessage->Height + 12;
                    FMessage->btnCancel->Left = (FMessage->Width - FMessage->btnCancel->Width) / 2;
                    FMessage->btnCancel->Visible = true;
                    FMessage->Show();
                    FMessage->Update();
                    FMessage->BringToFront();*/
                }
            }
            /*if(FMessage)
            {
                if(FMessage->Tag == mrCancel)
                   bStopSend = true;
                FMessage->laMessage->Caption = "\r\nConnecting Network. Please wait ...\r\n\r\n Time : " + IntToStr(gap + 1) + " Sec";
            }*/
            if(DNSConnect || bStopSend)
                break;
        }
        /*if(FMessage)
        {
            delete FMessage;
            FMessage = NULL;
        }*/
        //if(FGolfDataTime)
        //{
            //if(Setup.ReceiveAuto)
            //{
        //        FGolfDataTime->Label3->Caption = IntToStr(Setup.ReceiveGolfTime);
            //}
        //}
}
//---------------------------------------------------------------------------

bool __fastcall TFormDABITNet::ModuleData(BYTE ComKind, BYTE Addr, BYTE *Data, DWORD DataLen, int Number, OPCode OPC, bool bMessage)
{       
        bool res = false;
        bCRCFail = false;
        DWORD nowTick = 0;
        DWORD targetTick = 0;
        ZeroMemory(&RP, sizeof(RP));
        SchBmpFile = false;
        TransferData(ComKind, Addr, Data, DataLen, Number, OPC);
        nowTick = GetTickCount();
        if(FormMain->DelayRun)
        {
            targetTick = nowTick + FormMain->DelayTime;
            while(nowTick < targetTick)
            {
                nowTick = GetTickCount();
                Application->ProcessMessages();
            }
            res = true;
        }
        else
        {
            targetTick = GetTickCount() + FormMain->WaitTime;
            while(nowTick < targetTick)
            {
                nowTick = GetTickCount();
                Application->ProcessMessages();
                if(SchBmpFile)
                    break;
            }
            if(!SchBmpFile || bCRCFail)
            {
                asLogMessage = "Transfer FAIL";
                if(bCRCFail)
                    asLogMessage += " [CRC FAIL]";
                if(bMessage)
                    MessageDlg("\r\n" + asLogMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                res = false;
            }
            else
                res = true;
        }
        return res;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::TransferData(BYTE ComKind, BYTE Add, BYTE *Data, DWORD DataLen, int Number, OPCode OPC)
{
        int TotDataLen = 0;
        char *CDBuff;
        union{
            DWORD iSize;
            BYTE cSize[4];
        }uSize;
        union{
            unsigned int uiData;
            char cData[2];
        }uData;
        int bccvalue = 0;
        int cdSize = 0;
        bool tempBCC = false;
        if(OPC == FU)
            tempBCC = true;
        else
            tempBCC = FormMain->FlagCrcPacket;
        if(tempBCC)
            bccvalue = 2;
        int CRCDLESize = 0;
        int DLETextSize = 0;
        uSize.iSize = 1 + DataLen;
        CRCDLESize = uSize.iSize + bccvalue + 3;
        BYTE *CRCData = NULL;
        BYTE *DLEData = NULL;
        CRCData = new BYTE[CRCDLESize];
        ZeroMemory(CRCData, CRCDLESize);
        CRCData[0] = Add;
        if(tempBCC)
            uSize.iSize += 2;
        CRCData[1] = uSize.cSize[1];
        if(FormMain->DelayRun)
            CRCData[1] |= 0x20;
        if(tempBCC)
            CRCData[1] |= 0x40;
        CRCData[2] = uSize.cSize[0];
        if(FormMain->FlagDlePacket)
            CRCData[1] |= 0x80;
        CRCData[3] = OPC;
        memcpy(&CRCData[4], Data, DataLen);
        /*if(FlagDlePacket && OPC == CT)
        {
            DLETextSize = GetDLEDataSize(Data, DataLen);
            DLEData = new BYTE[CRCDLESize + DLETextSize + 2];
            ZeroMemory(DLEData, CRCDLESize + DLETextSize + 2);
            memcpy(DLEData, DLEDataConvert(CRCData, CRCDLESize), CRCDLESize + DLETextSize);
            if(tempBCC)
            {
                uData.uiData = Calc_crc(DLEData, CRCDLESize + DLETextSize - bccvalue);
                //uData.cData[0] = 0x10;
                //uData.cData[1] = 0x10;
                BYTE cdData[4];
                ZeroMemory(cdData, sizeof(cdData));
                cdData[0] = uData.cData[0];
                cdData[1] = uData.cData[1];
                if(cdData[0] == 0x10)
                {
                    cdSize++;
                    cdData[2] = cdData[1];
                    cdData[1] = 0x10;
                    if(cdData[2] == 0x10)
                    {
                        cdSize++;
                        cdData[3] = 0x10;
                    }
                }
                else if(cdData[1] == 0x10)
                {
                    cdSize++;
                    cdData[2] = 0x10;
                }
                memcpy(&DLEData[4 + DataLen + DLETextSize], cdData, bccvalue + cdSize);
            }
        }
        else
        {*/
            if(tempBCC)
            {
                uData.uiData = Calc_crc(CRCData, CRCDLESize - bccvalue);
                memcpy(&CRCData[4 + DataLen], uData.cData, bccvalue);
            }
        //}
        TotDataLen = 8 + bccvalue + DataLen + DLETextSize + cdSize;
        //TotDataLen = 8 + DataLen;
        CDBuff = new char[TotDataLen];
        ZeroMemory(CDBuff, TotDataLen);

        CDBuff[0] = DLE;
        CDBuff[1] = STX;
        if(FormMain->DelayRun)
            memcpy(&CDBuff[2], DLEData, CRCDLESize + DLETextSize + cdSize);
        else
            memcpy(&CDBuff[2], CRCData, CRCDLESize);
        CDBuff[2 + CRCDLESize + DLETextSize + cdSize] = DLE;
        //if(Setup.RS485)
        //    CDBuff[3 + CRCDLESize + DLETextSize + cdSize] = MTX;
        //else
            CDBuff[3 + CRCDLESize + DLETextSize + cdSize] = ETX;
        //if(Setup.FlagPacketLogView)
        //{
            asLogMessage = " ";
            if(Number <= 1)
            {
                for(int i = 0; i < TotDataLen; i++)
                    asLogMessage = asLogMessage + Int2HexConvert(CDBuff[i]) + " ";
            }
            else
            {
                for(int i = 0; i < 30; i++)
                    asLogMessage = asLogMessage + Int2HexConvert(CDBuff[i]) + " ";
                asLogMessage = asLogMessage + "~ ";
                for(int i = TotDataLen - 2; i < TotDataLen; i++)
                    asLogMessage = asLogMessage + Int2HexConvert(CDBuff[i]) + " ";
            }
        //}
        try{
            if(ComKind == 1)
            {
                if(!ClientSocket1->Socket->Connected)
                {
                    DNSConnect = false;
                    ClientSocket1->Open();
                    LANDelayCount(FormMain->ServerCommOpenDelay);
                    if(DNSConnect)
                        DNSConnect = false;
                }
                ClientSocket1->Socket->SendBuf(CDBuff, TotDataLen);
            }
            /*else if(ComKind == 2)
            {
                if(Clientsocket)
                {
                    if(Clientsocket->Connected)
                        Clientsocket->SendBuf(CDBuff, TotDataLen);
                }
            }*/
            else
            {
                //if(Setup.RS485)
                //    ComPort1->FlowControl->ControlRTS = rtsEnable;
                if(!ComPort1->Connected)
                    ComPort1->Open();
                ComPort1->Write(CDBuff, TotDataLen);
                //if(Setup.RS485)
                //    ComPort1->FlowControl->ControlRTS = rtsDisable;
            }
        }catch(...){
        }
        if(CRCData)
        {
            delete [] CRCData;
            CRCData = NULL;
        }
        if(DLEData)
        {
            delete [] DLEData;
            DLEData = NULL;
        }
        if(CDBuff)
        {
            delete [] CDBuff;
            CDBuff = NULL;
        }
}
//---------------------------------------------------------------------------

unsigned short int __fastcall TFormDABITNet::Calc_crc(unsigned char *buf, int numbytes)
{
      //unsigned int crc;
      unsigned char *ptr = buf, d;
      int i;
      int j;
      //crc = 0;
      static const unsigned short int wCRCTable[] = {
          0X0000, 0XC0C1, 0XC181, 0X0140, 0XC301, 0X03C0, 0X0280, 0XC241,
          0XC601, 0X06C0, 0X0780, 0XC741, 0X0500, 0XC5C1, 0XC481, 0X0440,
          0XCC01, 0X0CC0, 0X0D80, 0XCD41, 0X0F00, 0XCFC1, 0XCE81, 0X0E40,
          0X0A00, 0XCAC1, 0XCB81, 0X0B40, 0XC901, 0X09C0, 0X0880, 0XC841,
          0XD801, 0X18C0, 0X1980, 0XD941, 0X1B00, 0XDBC1, 0XDA81, 0X1A40,
          0X1E00, 0XDEC1, 0XDF81, 0X1F40, 0XDD01, 0X1DC0, 0X1C80, 0XDC41,
          0X1400, 0XD4C1, 0XD581, 0X1540, 0XD701, 0X17C0, 0X1680, 0XD641,
          0XD201, 0X12C0, 0X1380, 0XD341, 0X1100, 0XD1C1, 0XD081, 0X1040,
          0XF001, 0X30C0, 0X3180, 0XF141, 0X3300, 0XF3C1, 0XF281, 0X3240,
          0X3600, 0XF6C1, 0XF781, 0X3740, 0XF501, 0X35C0, 0X3480, 0XF441,
          0X3C00, 0XFCC1, 0XFD81, 0X3D40, 0XFF01, 0X3FC0, 0X3E80, 0XFE41,
          0XFA01, 0X3AC0, 0X3B80, 0XFB41, 0X3900, 0XF9C1, 0XF881, 0X3840,
          0X2800, 0XE8C1, 0XE981, 0X2940, 0XEB01, 0X2BC0, 0X2A80, 0XEA41,
          0XEE01, 0X2EC0, 0X2F80, 0XEF41, 0X2D00, 0XEDC1, 0XEC81, 0X2C40,
          0XE401, 0X24C0, 0X2580, 0XE541, 0X2700, 0XE7C1, 0XE681, 0X2640,
          0X2200, 0XE2C1, 0XE381, 0X2340, 0XE101, 0X21C0, 0X2080, 0XE041,
          0XA001, 0X60C0, 0X6180, 0XA141, 0X6300, 0XA3C1, 0XA281, 0X6240,
          0X6600, 0XA6C1, 0XA781, 0X6740, 0XA501, 0X65C0, 0X6480, 0XA441,
          0X6C00, 0XACC1, 0XAD81, 0X6D40, 0XAF01, 0X6FC0, 0X6E80, 0XAE41,
          0XAA01, 0X6AC0, 0X6B80, 0XAB41, 0X6900, 0XA9C1, 0XA881, 0X6840,
          0X7800, 0XB8C1, 0XB981, 0X7940, 0XBB01, 0X7BC0, 0X7A80, 0XBA41,
          0XBE01, 0X7EC0, 0X7F80, 0XBF41, 0X7D00, 0XBDC1, 0XBC81, 0X7C40,
          0XB401, 0X74C0, 0X7580, 0XB541, 0X7700, 0XB7C1, 0XB681, 0X7640,
          0X7200, 0XB2C1, 0XB381, 0X7340, 0XB101, 0X71C0, 0X7080, 0XB041,
          0X5000, 0X90C1, 0X9181, 0X5140, 0X9301, 0X53C0, 0X5280, 0X9241,
          0X9601, 0X56C0, 0X5780, 0X9741, 0X5500, 0X95C1, 0X9481, 0X5440,
          0X9C01, 0X5CC0, 0X5D80, 0X9D41, 0X5F00, 0X9FC1, 0X9E81, 0X5E40,
          0X5A00, 0X9AC1, 0X9B81, 0X5B40, 0X9901, 0X59C0, 0X5880, 0X9841,
          0X8801, 0X48C0, 0X4980, 0X8941, 0X4B00, 0X8BC1, 0X8A81, 0X4A40,
          0X4E00, 0X8EC1, 0X8F81, 0X4F40, 0X8D01, 0X4DC0, 0X4C80, 0X8C41,
          0X4400, 0X84C1, 0X8581, 0X4540, 0X8701, 0X47C0, 0X4680, 0X8641,
          0X8201, 0X42C0, 0X4380, 0X8341, 0X4100, 0X81C1, 0X8081, 0X4040 };
      unsigned short int wCRCWord = 0x0000;
      for(j = 0; j < numbytes; j++)
      {
          d = (unsigned short int)*ptr++ ^ wCRCWord;
          wCRCWord >>= 8;
          wCRCWord  ^= wCRCTable[d];
      }
      return(wCRCWord);
}
//--------------------------------------------------------------------------

BYTE *__fastcall TFormDABITNet::DLEDataConvert(BYTE *DLEData, int Size)
{
        ZeroMemory(RedData, sizeof(RedData));
        int DLECnt = 0;
        for(int i = 0; i < Size; i++)
        {
            if(DLEData[i] == 0x10)
                RedData[DLECnt++] = DLEData[i];
            RedData[DLECnt++] = DLEData[i];
        }
        return RedData;
}
//--------------------------------------------------------------------------

int __fastcall TFormDABITNet::GetDLEDataSize(BYTE *DLEData, int Size)
{
        int GetSize = 0;
        for(int i = 0; i < Size; i++)
        {
            if(DLEData[i] == 0x10)
                GetSize++;
        }
        return GetSize;
}
//--------------------------------------------------------------------------

AnsiString __fastcall TFormDABITNet::Int2HexConvert(BYTE Value)
{
        AnsiString asResult = "";
        union{
            struct BIT {
                unsigned b0 : 4;
                unsigned b1 : 4;
            } bit;
            BYTE cData;
        }uData;
        uData.cData = Value;
        if(uData.bit.b1 ==  10)
            asResult = "A";
        else if(uData.bit.b1 ==  11)
            asResult = "B";
        else if(uData.bit.b1 ==  12)
            asResult = "C";
        else if(uData.bit.b1 ==  13)
            asResult = "D";
        else if(uData.bit.b1 ==  14)
            asResult = "E";
        else if(uData.bit.b1 >=  15)
            asResult = "F";
        else
            asResult += IntToStr(uData.bit.b1);
        if(uData.bit.b0 ==  10)
            asResult += "A";
        else if(uData.bit.b0 ==  11)
            asResult += "B";
        else if(uData.bit.b0 ==  12)
            asResult += "C";
        else if(uData.bit.b0 ==  13)
            asResult += "D";
        else if(uData.bit.b0 ==  14)
            asResult += "E";
        else if(uData.bit.b0 >=  15)
            asResult += "F";
        else
            asResult += IntToStr(uData.bit.b0);
        return asResult;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::ReadParsing(bool ComKind, char Add, BYTE *ParsingData, int ParsingSize)
{
        BYTE ch;
        for(int i = 0; i < ParsingSize; i++)
        {
            ch = ParsingData[i];
            switch(RP.CS)
            {
                case SynSDLEHead :
                {
                    if(ch == DLE)
                        RP.CS = SynSTXHead;
                    else if(ch == 'D')
                    {
                        RP.Data[RP.Rcnt++] = ch;
                        RP.CS = SynBlock;
                    }
                    else
                        ZeroMemory(&RP, sizeof(RP));
                    break;
                }
                case SynSTXHead :
                {
                    if(ch == STX)
                        RP.CS = SynAddress;
                    else
                        ZeroMemory(&RP, sizeof(RP));
                    break;
                }
                case SynAddress :
                {
                    RP.CS = SynHLen;
                    RP.Add = ch;
                    break;
                }
                case SynSendID :
                {
                    RP.SendID = ch;
                    RP.CS = SynOPCode;
                    break;
                }
                case SynHLen :
                {
                    RP.HLen = ch;
                    RP.CS = SynLLen;
                    break;
                }
                case SynLLen :
                {                      
                    RP.LLen = ch;      
                    RP.CS = SynOPCode; 
                    break;             
                }                      
                case SynOPCode :       
                {
                    if(ch == AN)
                    {
                        RP.OPCode = ch;
                        /*if(ch == EM)
                        {
                                Status = "ACK";
                                RP.CS = SynDataField;
                        }
                        else*/
                        //if(ch != AD)
                        //        RP.CS = SynEDLEHead;
                        //else
                        RP.CS = SynDataField;
                    }
                    else
                        ZeroMemory(&RP, sizeof(RP));
                    break;
                }
                /*case SynStatus :
                {
                    if(ch == ACK)
                    {
                        RP.Status = ch;
                        Status = "ACK";
                        RP.CS = SynDataField;
                    }
                    else if(ch == NAK)
                    {
                        RP.Status = ch;
                        Status = "NAK";
                        RP.CS = SynDataField;
                    }
                    else
                            ZeroMemory(&RP, sizeof(RP));
                    break;
                }*/
                case SynDataField :
                {
                    int dl = 0;
                    int rdl = 0;
                    bool btempBCC = false;
                    if(RP.OPCode == FU)
                        btempBCC = true;
                    else
                        btempBCC = FormMain->FlagCrcPacket;
                    RP.bDLE = FormMain->FlagDlePacket;
                    //if(FormMain->FlagDlePacket)
                    if(FormMain->FlagDlePacket && (RP.OPCode == NOD || RP.OPCode == ND || RP.OPCode == PC || RP.OPCode == CT || RP.OPCode == RCT || RP.bDLE))
                    {
                        rdl = 256 * 256;
                        if(ch == DLE || FormMain->FlagDlePacket)
                        {
                            if(i + 1 == ParsingSize)
                            {
                                FormMain->FlagDlePacket = true;
                                if(ch == ETX)
                                {
                                    if(btempBCC)
                                    {
                                        RP.DELLen -= 2;
                                        //if(RP.DELLen <= 0)
                                        if(!(RP.HLen & 0x40))
                                        {
                                            bCRCFail = true;
                                            RP.CS = SynETXHead;
                                            continue;
                                        }
                                        RP.Bcc[0] = RP.Data[RP.DELLen];
                                        RP.Bcc[1] = RP.Data[RP.DELLen + 1];
                                        union{
                                            unsigned int uiData;
                                            char cData[2];
                                        }uData;
                                        BYTE tempBCC[2];
                                        BYTE *CRCData = NULL;
                                        BYTE *DLEData = NULL;
                                        int DLETextSize = 0;
                                        int CRCDLESize = 0;
                                        CRCDLESize = RP.DELLen + 4;
                                        try{
                                            ZeroMemory(tempBCC, sizeof(tempBCC));
                                            CRCData = new BYTE[CRCDLESize];
                                            ZeroMemory(CRCData, CRCDLESize);
                                            CRCData[0] = RP.Add;
                                            CRCData[1] = RP.HLen;
                                            CRCData[2] = RP.LLen;
                                            CRCData[3] = RP.OPCode;
                                            memcpy(&CRCData[4], RP.Data, RP.DELLen);
                                            DLETextSize = GetDLEDataSize(RP.Data, RP.DELLen);
                                            DLEData = new BYTE[CRCDLESize + DLETextSize];
                                            ZeroMemory(DLEData, CRCDLESize + DLETextSize);
                                            memcpy(DLEData, DLEDataConvert(CRCData, CRCDLESize), CRCDLESize + DLETextSize);
                                            if(btempBCC)
                                            {
                                              uData.uiData = Calc_crc(DLEData, CRCDLESize + DLETextSize);
                                              memcpy(&tempBCC, uData.cData, sizeof(tempBCC));
                                            }
                                        }catch(...){
                                        }
                                        if(RP.Bcc[0] != tempBCC[0] || RP.Bcc[1] != tempBCC[1])
                                            bCRCFail = true;
                                        else
                                            bCRCFail = false;
                                        if(CRCData)
                                        {
                                            delete [] CRCData;
                                            CRCData = NULL;
                                        }
                                        if(DLEData)
                                        {
                                            delete [] DLEData;
                                            DLEData = NULL;
                                        }
                                    }
                                    else
                                        RP.DELLen++;
                                    i -= 1;
                                    RP.CS = SynETXHead;
                                }
                                continue;
                            }
                            else
                            {
                                if(FormMain->FlagDlePacket)
                                {
                                    FormMain->FlagDlePacket = false;
                                    ch = ParsingData[i];
                                    if(ch == DLE)
                                        RP.Data[RP.Rcnt++] = ch;
                                }
                                else
                                    ch = ParsingData[i + 1];
                            }
                            if(ch == DLE)
                                continue;
                            else if(ch == ETX)
                            {
                                if(btempBCC)
                                {
                                    RP.DELLen -= 2;
                                    //if(RP.DELLen <= 0)
                                    if(!(RP.HLen & 0x40))
                                    {
                                        bCRCFail = true;
                                        RP.CS = SynETXHead;
                                        continue;
                                    }
                                    RP.Bcc[0] = RP.Data[RP.DELLen];
                                    RP.Bcc[1] = RP.Data[RP.DELLen + 1];
                                    union{
                                        unsigned int uiData;
                                        char cData[2];
                                    }uData;
                                    BYTE tempBCC[2];
                                    BYTE *CRCData = NULL;
                                    BYTE *DLEData = NULL;
                                    int DLETextSize = 0;
                                    int CRCDLESize = 0;
                                    CRCDLESize = RP.DELLen + 4;
                                    try{
                                        ZeroMemory(tempBCC, sizeof(tempBCC));
                                        CRCData = new BYTE[CRCDLESize];
                                        ZeroMemory(CRCData, CRCDLESize);
                                        CRCData[0] = RP.Add;
                                        CRCData[1] = RP.HLen;
                                        CRCData[2] = RP.LLen;
                                        CRCData[3] = RP.OPCode;
                                        memcpy(&CRCData[4], RP.Data, RP.DELLen);
                                        DLETextSize = GetDLEDataSize(RP.Data, RP.DELLen);
                                        DLEData = new BYTE[CRCDLESize + DLETextSize];
                                        ZeroMemory(DLEData, CRCDLESize + DLETextSize);
                                        memcpy(DLEData, DLEDataConvert(CRCData, CRCDLESize), CRCDLESize + DLETextSize);
                                        if(btempBCC)
                                        {
                                            uData.uiData = Calc_crc(DLEData, CRCDLESize + DLETextSize);
                                            memcpy(&tempBCC, uData.cData, sizeof(tempBCC));
                                        }
                                    }catch(...){
                                    }
                                    if(RP.Bcc[0] != tempBCC[0] || RP.Bcc[1] != tempBCC[1])
                                        bCRCFail = true;
                                    else
                                        bCRCFail = false;
                                    if(CRCData)
                                    {
                                        delete [] CRCData;
                                        CRCData = NULL;
                                    }
                                    if(DLEData)
                                    {
                                        delete [] DLEData;
                                        DLEData = NULL;
                                    }
                                }
                                else
                                    RP.DELLen++;
                                RP.CS = SynETXHead;
                                continue;
                            }
                            else
                                RP.DELLen++;
                        }
                        else
                            RP.DELLen++;
                    }
                    else
                    {
                        union{
                            BYTE cbSize;
                            struct{
                                unsigned bit0 : 5;
                                unsigned bit1 : 1;
                                unsigned bit2 : 1;
                                unsigned bit3 : 1;
                            }Bit;
                        }uSize;
                        uSize.cbSize = RP.HLen;
                        dl = (int)uSize.Bit.bit0 * 256 + (int)RP.LLen;
                        if(btempBCC)
                            rdl = dl - 1 - sizeof(RP.Bcc);
                        else
                            rdl = dl - 1;
                    }
                    ch = ParsingData[i];
                    RP.Data[RP.Rcnt++] = ch;
                    /*if(Setup.RS485)
                    {
                        if(ch == DLE)
                            RP.TempData[RP.TRcnt++] = DLE;
                        else if((RP.TempData[0] == DLE && ch == STX) || (Setup.ProtocolKind && ch == STX))
                        {
                            RP.CS = SynAddress;
                            ZeroMemory(RP.TempData, sizeof(RP.TempData));
                            RP.TRcnt = 0;
                            break;
                        }
                        else
                        {
                            ZeroMemory(RP.TempData, sizeof(RP.TempData));
                            RP.TRcnt = 0;
                        }
                    }*/
                    if(RP.Rcnt >= rdl)
                    {
                        if(RP.OPCode == RFD)
                        {
                            /*CurRevSch.SchNo = (BYTE)RP.Data[0];
                            CurRevSch.TotSchCnt = (BYTE)RP.Data[1];
                            CurRevSch.ImageType = (BYTE)RP.Data[2];
                            CurRevSch.FileNo  = (BYTE)RP.Data[3];
                            CurRevSch.EEffect = (BYTE)RP.Data[4];
                            CurRevSch.MEffect = (BYTE)RP.Data[5];
                            CurRevSch.LEffect = (BYTE)RP.Data[6];
                            CurRevSch.ESpeed =  (BYTE)RP.Data[7];
                            CurRevSch.MSpeed =  (BYTE)RP.Data[8];
                            CurRevSch.LSpeed =  (BYTE)RP.Data[9];
                            CurRevSch.Delay =   (BYTE)RP.Data[10];*/
                            //RP.Rcnt = 0;
                            if(btempBCC)
                                RP.CS = SynBcc;
                            else
                            {
                                RP.CS = SynEDLEHead;
                            }
                        }
                        else if(RP.OPCode == RPD)
                        {
                            //memcpy(&Parameter, RP.Data, sizeof(Parameter));
                            //AnsiString ParamFileName = BasePath + IncludeTrailingPathDelimiter(DestSDDataPath) + "param000.set";
                            //RoadSaveParamFile(ParamFileName, 3);
                            //RP.Rcnt = 0;
                            if(btempBCC)
                                RP.CS = SynBcc;
                            else
                            {
                                RP.CS = SynEDLEHead;
                            }
                        }
                        else if(RP.OPCode == CVI || RP.OPCode == CDSR || RP.OPCode == RSD || RP.OPCode == RID || RP.OPCode == FDPI)
                        {
                            //RP.Rcnt = 0;
                            if(btempBCC)
                                RP.CS = SynBcc;
                            else
                            {
                                RP.CS = SynEDLEHead;
                            }
                        }
                        else
                        {
                            RP.Status = RP.Data[0];
                            if(RP.Status == ACK)
                                Status = "ACK";
                            else if(RP.Status == NAK)
                                Status = "NAK";
                            else
                                Status = "ACK?";
                            //RP.Rcnt = 0;
                            if(btempBCC)
                                RP.CS = SynBcc;
                            else
                                RP.CS = SynEDLEHead;
                        }
                    }
                    break;
                }
                case SynBcc :
                {
                    RP.Bcc[RP.BCCcnt++] = ch;
                    if(RP.BCCcnt >= sizeof(RP.Bcc))
                    {
                        union{
                            unsigned int uiData;
                            char cData[2];
                        }uData;
                        union{
                            BYTE cbSize;
                            struct{
                                unsigned bit0 : 5;
                                unsigned bit1 : 1;
                                unsigned bit2 : 1;
                                unsigned bit3 : 1;
                            }Bit;
                        }uSize;
                        int dl = 0;
                        uSize.cbSize = RP.HLen;
                        dl = (int)uSize.Bit.bit0 * 256 + (int)RP.LLen - 2;
                        BYTE tempBCC[2];
                        ZeroMemory(tempBCC, sizeof(tempBCC));
                        BYTE *CRCData = new BYTE[dl + 3];
                        ZeroMemory(CRCData, dl + 3);
                        try{
                            CRCData[0] = RP.Add;
                            CRCData[1] = RP.HLen;
                            CRCData[2] = RP.LLen;
                            CRCData[3] = RP.OPCode;
                            memcpy(&CRCData[4], RP.Data, dl - 1);
                            uData.uiData = Calc_crc(CRCData, dl + 3);
                            memcpy(&tempBCC, uData.cData, sizeof(tempBCC));
                        }catch(...){
                        }
                        if(RP.Bcc[0] != tempBCC[0] || RP.Bcc[1] != tempBCC[1])
                            bCRCFail = true;
                        else
                            bCRCFail = false;
                        if(CRCData)
                        {
                            delete [] CRCData;
                            CRCData = NULL;
                        }
                        RP.CS = SynEDLEHead;
                    }
                    break;
                }
                case SynEDLEHead :
                {
                    if(ch == DLE)
                        RP.CS = SynETXHead;
                    else
                        ZeroMemory(&RP, sizeof(RP));
                    break;
                }
                case SynETXHead :
                {
                    //bool btempBCC = false;
                    //int dl = 0;
                    //if(RP.OPCode == FU)
                    //    btempBCC = true;
                    //else
                    //    btempBCC = FormMain->FlagCrcPacket;
                    if(ch == ETX)
                    {   
                        union{
                            BYTE cbSize;
                            struct{
                                unsigned bit0 : 5;
                                unsigned bit1 : 1;
                                unsigned bit2 : 1;
                                unsigned bit3 : 1;
                            }Bit;
                        }uSize;
                        uSize.cbSize = RP.HLen;
                        //dl = (int)uSize.Bit.bit0 * 256 + (int)RP.LLen;
                        //if(btempBCC)
                        //    dl -= 2;
                        BYTE bTempData[1];
                        bTempData[0] = 0x00;
                        SchBmpFile = true;
                        switch(RP.OPCode)
                        {
                            case AN :
                            {
                                RecieveData((char*)RP.Data, RP.Rcnt);
                                break;
                            }
                        }
                        /*if(Setup.FlagPacketLogView)
                        {
                            asLogMessage = asLogMessage + "\r\n                \t Receive : 10 02 " + Int2HexConvert(RP.Add) + " " + Int2HexConvert(RP.HLen) + " "
                                 + Int2HexConvert(RP.LLen) + " " + Int2HexConvert(RP.OPCode) + " ";
                            if(dl - 1 < 25)
                            {
                                for(int j = 0; j < dl - 1; j++)
                                    asLogMessage = asLogMessage + Int2HexConvert(RP.Data[j]) + " ";
                            }
                            else
                            {
                                for(int i = 0; i < 30; i++)
                                    asLogMessage = asLogMessage + Int2HexConvert(RP.Data[i]) + " ";
                                asLogMessage = asLogMessage + "~ ";
                                if(btempBCC)
                                {
                                    for(int i = dl - 1 - 4; i < dl - 1 - 2; i++)
                                        asLogMessage = asLogMessage + Int2HexConvert(RP.Data[i]) + " ";
                                    for(int j = 0; j < sizeof(RP.Bcc); j++)
                                        asLogMessage = asLogMessage + Int2HexConvert(RP.Bcc[j]) + " ";
                                }
                                else
                                {
                                    for(int i = dl - 1 - 2; i < dl - 1; i++)
                                        asLogMessage = asLogMessage + Int2HexConvert(RP.Data[i]) + " ";
                                }
                            }
                            asLogMessage += "10 03 ";
                            AddLog("", asLogMessage, clBlack);
                        }*/
                    }
                    ZeroMemory(&RP, sizeof(RP));
                    break;
                }
            }
        }
}
//---------------------------------------------------------------------------

AnsiString __fastcall TFormDABITNet::GetDNS(int iType)
{
        AnsiString result;
        //... GetNetworkParams
        FIXED_INFO * FixedInfo;
        ULONG    ulOutBufLen;
        DWORD    dwRetVal = 0;
        IP_ADDR_STRING * pIPAddr;
        FixedInfo = (FIXED_INFO *) GlobalAlloc( GPTR, sizeof( FIXED_INFO ) );
        ulOutBufLen = sizeof( FIXED_INFO );
        if( ERROR_BUFFER_OVERFLOW == GetNetworkParams( FixedInfo, &ulOutBufLen ) )
        {
            GlobalFree( FixedInfo );
            FixedInfo = (FIXED_INFO*)GlobalAlloc( GPTR, ulOutBufLen );
        }
        dwRetVal = GetNetworkParams( FixedInfo, &ulOutBufLen );
        if(dwRetVal)
        {
    //        ShowMessage("Error : ");
        }
        else
        {
            //lb->Items->Add("**** GetNetworkParams ******");
            //result = "Host Name : ";
            if(iType == 1)
                result = FixedInfo->DomainName;
            else
                result = FixedInfo->DnsServerList.IpAddress.String;
            /*lb->Items->Add(result);
            result = "Domain Name: ";
            result += FixedInfo->DomainName;
            lb->Items->Add(result);
            lb->Items->Add("DNS Servers :" );
            result = "IP add : ";
            result += FixedInfo->DnsServerList.IpAddress.String;
            lb->Items->Add(result);
            pIPAddr = FixedInfo->DnsServerList.Next;
            while ( pIPAddr )
            {
                result = "IP add : ";
                result += pIPAddr ->IpAddress.String;
                lb->Items->Add(result);
                pIPAddr = pIPAddr->Next;
            }*/
        }
        //.........
        if(FixedInfo)
            GlobalFree( FixedInfo );
        return result;
}
//---------------------------------------------------------------------------

AnsiString __fastcall TFormDABITNet::GetNetwork(int iType)
{
        AnsiString AddressStr = "";
        DWORD dwRet;
        PIP_ADAPTER_ADDRESSES pAdpAddrs;
        PIP_ADAPTER_ADDRESSES pThis;
        PIP_ADAPTER_UNICAST_ADDRESS pThisAddrs;
        unsigned long ulBufLen = sizeof(IP_ADAPTER_ADDRESSES);

        pAdpAddrs = (PIP_ADAPTER_ADDRESSES)malloc( ulBufLen );
        if ( !pAdpAddrs ) return AddressStr;

        dwRet = GetAdaptersAddresses(AF_INET, 0, NULL, pAdpAddrs, &ulBufLen);
        if (dwRet == ERROR_BUFFER_OVERFLOW)
        {
          free ( pAdpAddrs );
          pAdpAddrs = (PIP_ADAPTER_ADDRESSES)malloc( ulBufLen );

          if ( !pAdpAddrs ) return AddressStr;
        }

        dwRet = GetAdaptersAddresses(AF_INET, 0, NULL, pAdpAddrs, &ulBufLen);
        if ( dwRet != NO_ERROR )
        {
          free ( pAdpAddrs );
          return AddressStr;
        }
        bool bFindAddr = false;
        for ( pThis = pAdpAddrs; NULL != pThis; pThis = pThis->Next)
        {
          AddressStr = pThis->FriendlyName;
          if(pThis->OperStatus == IfOperStatusDown)
              continue;
          //fprintf(stderr," DS: %S\n", pThis->Description);
          //fprintf(stderr," AN: %S\n", pThis->AdapterName);
          for ( pThisAddrs = pThis->FirstUnicastAddress;
                NULL != pThisAddrs;
                pThisAddrs = pThisAddrs->Next )
          {
            struct sockaddr_in* pAddr
              = (struct sockaddr_in*)pThisAddrs->Address.lpSockaddr;
            AddressStr = inet_ntoa(pAddr->sin_addr);
          }
          if(AddressStr != NULL)
          {
              AddressStr = pThis->FriendlyName;
              bFindAddr = true;
              break;
          }
          if(bFindAddr)
              break;
        }

        free ( pAdpAddrs );
        return AddressStr;
}
//---------------------------------------------------------------------------

bool __fastcall TFormDABITNet::ComPortSet(BYTE ComKind, AnsiString PortUse, BYTE BaudRateUse, BYTE DTR, BYTE RTS, AnsiString IPAddress, int IPPort, bool bMessage)
{
        AnsiString asMessage = "";
        if(ComKind == 0 || (ComKind == 1 && ClientSocket1->Socket->Connected))
            ComClose(ComKind);
        bool tempMsg = false;
        if(ComKind == 0)
        {
            ComPort1->Port = PortUse;
            AnsiString asBR = "";
            switch(BaudRateUse)
            {
                case 0 :
                    ComPort1->BaudRate = br2400;
                    asBR = ": 2,400bps";
                    break;
                case 1 :
                    ComPort1->BaudRate = br4800;
                    asBR = ": 4,800bps";
                    break;
                case 2 :
                    ComPort1->BaudRate = br9600;
                    asBR = ": 9,600bps";
                    break;
                case 3 :
                    ComPort1->BaudRate = br19200;
                    asBR = ": 19,200bps";
                    break;
                case 4 :
                    ComPort1->BaudRate = br38400;
                    asBR = ": 38,400bps";
                    break;
                case 5 :
                    ComPort1->BaudRate = br57600;
                    asBR = ": 57,600bps";
                    break;
                case 6 :
                    ComPort1->BaudRate = br115200;
                    asBR = ": 115,200bps";
                    break;
                case 7 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 230400;
                    asBR = ": 230,400bps";
                    break;
                case 8 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 460800;
                    asBR = ": 460,800bps";
                    break;
                case 9 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 921600;
                    asBR = ": 921,600bps";
                    break;
                case 10 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 1843200;
                    asBR = ": 1,843,200bps";
                    break;
                case 11 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 3686400;
                    asBR = ": 3,686,400bps";
                    break;
                case 12 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 7372800;
                    asBR = ": 7,372,800bps";
                    break;
                case 13 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 14745600;
                    asBR = ": 14,745,600bps";
                    break;
                case 14 :
                    ComPort1->BaudRate = brCustom;
                    ComPort1->CustomBaudRate = 29491200;
                    asBR = ": 29,491,200bps";
                    break;
                default :
                    ComPort1->BaudRate = br115200;
                    asBR = ": 115,200bps";
                    break;
            }
            switch(DTR)
            {
                case 0 :
                    ComPort1->FlowControl->ControlDTR = dtrDisable;
                    break;
                case 1 :
                    ComPort1->FlowControl->ControlDTR = dtrEnable;
                    break;
                case 2 :
                    ComPort1->FlowControl->ControlDTR = dtrHandshake;
                    break;
                default :
                    ComPort1->FlowControl->ControlDTR = dtrDisable;
                    break;
            }
            switch(RTS)
            {
                case 0 :
                    ComPort1->FlowControl->ControlRTS = rtsDisable;
                    break;
                case 1 :
                    ComPort1->FlowControl->ControlRTS = rtsEnable;
                    break;
                case 2 :
                    ComPort1->FlowControl->ControlRTS = rtsHandshake;
                    break;
                case 3 :
                    ComPort1->FlowControl->ControlRTS = rtsToggle;
                    break;
                default :
                    ComPort1->FlowControl->ControlRTS = rtsDisable;
                    break;
            }
            if(!ComPort1->Connected)
            {
                try{
                    ComPort1->Open();
                    asMessage = ComPort1->Port + " " + asBR + ", 8, 1, N - Open [OK]";
                    FormMain->AddLog(asMessage, clBlack);
                }catch(...){
                    asMessage = ComPort1->Port + " " + asBR + ", 8, 1, N - Open [FAIL]";
                    FormMain->AddLog(asMessage, clRed);
                    if(bMessage)
                        MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                    return false;
                }
            }
        }
        else
        {
            /*try{
                bDDNS = false;
                int tempIP = StrToIntDef(IPAddress.SubString(1, IPAddress.Pos(".") - 1), -1);
                if(tempIP < 0)
                    bDDNS = true;
                if(bDDNS)
                {
                    try{
                        SchBmpFile = false;
                        DNSAddress = "";
                        //bStopSend = false;
                        IdIcmpClient1->Host = IPAddress;
                        IdIcmpClient1->ReceiveTimeout = 1000;
                        DNSConnect = false;
                        IdIcmpClient1->Ping();
                        LANDelayCount(3000);
                        if(DNSAddress == "" || DNSAddress == " ")
                        {
                            IdDNSResolver->QueryRecords = IdDNSResolver->QueryRecords<<qtA;
                            IdDNSResolver->Host = GetDNS();
                            IdDNSResolver->Active = true;
                            IdDNSResolver->Resolve(IPAddress);
                            IdDNSResolver->Active = false;
                            for(int i = 0; i < IdDNSResolver->QueryResult->Count; i++)
                            {
                                switch(IdDNSResolver->QueryResult->Items[i]->RecType)
                                {
                                    case qtA:
                                    {
                                        DNSAddress = dynamic_cast<TARecord *>(IdDNSResolver->QueryResult->Items[i])->IPAddress;
                                        DNSConnect = true;
                                    }
                                    break;
                                }
                                if(DNSConnect)
                                    break;
                            }
                        }
                    }catch(...){
                        tempMsg = true;
                        try{
                            if(DNSAddress == "" || DNSAddress == " ")
                            {
                                IdDNSResolver->QueryRecords = IdDNSResolver->QueryRecords<<qtA;
                                IdDNSResolver->Host = GetDNS();
                                IdDNSResolver->Active = true;
                                IdDNSResolver->Resolve(IPAddress);
                                IdDNSResolver->Active = false;
                                for(int i = 0; i < IdDNSResolver->QueryResult->Count; i++)
                                {
                                    switch(IdDNSResolver->QueryResult->Items[i]->RecType)
                                    {
                                        case qtA:
                                        {
                                            DNSAddress = dynamic_cast<TARecord *>(IdDNSResolver->QueryResult->Items[i])->IPAddress;
                                            DNSConnect = true;
                                            ShowMessage("ping true 3" + DNSAddress);
                                        }
                                        break;
                                    }
                                    if(DNSConnect)
                                        break;
                                }
                                tempMsg = false;
                            }
                        }catch(...){
                            if(bMessage)
                            {
                                asMessage = "IP:" + DNSAddress + ", PORT:" + IntToStr(IPPort) + " Open [FAIL]";
                                MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                            }
                            DNSConnect = false;
                            DNSAddress = "";
                            return false;
                        }
                        if(tempMsg && bMessage)
                        {
                            asMessage = "IP:" + DNSAddress + ", PORT:" + IntToStr(IPPort) + " Open [FAIL]";
                            MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                        }
                        if(tempMsg)
                        {
                            DNSConnect = false;
                            DNSAddress = "";
                            return false;
                        }
                    }
                    if(DNSConnect)
                    {
                        ClientSocket1->Address = DNSAddress;
                        ClientSocket1->Port = IPPort;
                        DNSConnect = false;
                    }
                    else
                    {
                        ClientSocket1->Address = "127.0.0.1";
                        ClientSocket1->Port = IPPort;
                    }
                }
                else
                {
                    ClientSocket1->Address = IPAddress;
                    ClientSocket1->Port = IPPort;
                }
            }catch(...){
                asMessage = "IP:" + ClientSocket1->Address + ", PORT:" + IntToStr(ClientSocket1->Port) + " Open [FAIL]";
                MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                return false;
            }
            try{
                DNSConnect = false;
                ClientSocket1->Open();
                LANDelayCount(ServerCommOpenDelay);
                if(!DNSConnect)
                {
                    if(bMessage)
                    {
                        asMessage = "IP:" + ClientSocket1->Address + ", PORT:" + IntToStr(ClientSocket1->Port) + " Open [FAIL]";
                        MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                    }
                    return false;
                }
                else
                    DNSConnect = false;
            }catch(...){
                if(bMessage)
                {
                    asMessage = "IP:" + ClientSocket1->Address + ", PORT:" + IntToStr(ClientSocket1->Port) + " Open [FAIL]";
                    MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                }
                return false;
            }*/
        }
        return true;
}
//---------------------------------------------------------------------------

bool __fastcall TFormDABITNet::ComClose(BYTE ComKind)
{
        if(FormMain->DelayRun == 1 && FormMain->DelayTime == 0)
            NoneDelayCount(300);
        AnsiString asMessage = "";
        if(ComKind == 0)
        {
            try{
                ComPort1->Close();
                asMessage = ComPort1->Port + " Close [OK]";
                FormMain->AddLog(asMessage, clBlack);
            }catch(...){
                asMessage = ComPort1->Port + " Close [FAIL]";
                FormMain->AddLog(asMessage, clRed);
                MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                return false;
            }
        }
        else
        {
            try{
                ClientSocket1->Close();
                asMessage = "IP:" + ClientSocket1->Address + ", PORT:" + IntToStr(ClientSocket1->Port) + " Close [OK]";
                FormMain->AddLog(asMessage, clBlack);
            }catch(...){
                asMessage = "IP:" + ClientSocket1->Address + ", PORT:" + IntToStr(ClientSocket1->Port) + " Close [FAIL]";
                FormMain->AddLog(asMessage, clRed);
                MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                return false;
            }
        }
        return true;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BtnConnectClick(TObject *Sender)
{
        AnsiString IPAddress = eServerName->Text;
        try{
            SchBmpFile = false;
            DNSAddress = "";
            //bStopSend = false;
            IdIcmpClient1->Host = IPAddress;
            IdIcmpClient1->ReceiveTimeout = 1000;
            DNSConnect = false;
            IdIcmpClient1->Ping();
            LANDelayCount(3000);
            if(DNSAddress == "" || DNSAddress == " ")
            {
                IdDNSResolver->QueryRecords = IdDNSResolver->QueryRecords<<qtA;
                IdDNSResolver->Host = GetDNS(0);
                IdDNSResolver->Active = true;
                IdDNSResolver->Resolve(IPAddress);
                IdDNSResolver->Active = false;
                for(int i = 0; i < IdDNSResolver->QueryResult->Count; i++)
                {
                    switch(IdDNSResolver->QueryResult->Items[i]->RecType)
                    {
                        case qtA:
                        {
                            DNSAddress = dynamic_cast<TARecord *>(IdDNSResolver->QueryResult->Items[i])->IPAddress;
                            DNSConnect = true;
                        }
                        break;
                    }
                    if(DNSConnect)
                        break;
                }
            }
            eServerIPAddr->Text = DNSAddress;
        }catch(...){
            AnsiString asMessage = "IPAddress:" + IPAddress + " [FAIL]";
            MessageDlg("\r\n" + asMessage +  "\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::rbServerIPClick(TObject *Sender)
{
        laServerIP->Enabled = true;
        eServerIPAddr->Enabled = true;
        eServerName->Enabled = false;
        laServerDDNS->Enabled = false;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::rbServerNameClick(TObject *Sender)
{
        laServerIP->Enabled = false;
        eServerIPAddr->Enabled = false;
        eServerName->Enabled = true;
        laServerDDNS->Enabled = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::rbEthernetSetClick(TObject *Sender)
{
        //rgComMode->Enabled = rbEthernetSet->Checked;
        //TntLabel3->Enabled = rbEthernetSet->Checked;
        //eIPAddr->Enabled = rbEthernetSet->Checked;
        //TntLabel7->Enabled = rbEthernetSet->Checked;
        //ePort->Enabled = rbEthernetSet->Checked;
        //TntLabel4->Enabled = rbEthernetSet->Checked;
        //eSubnetMask->Enabled = rbEthernetSet->Checked;
        //TntLabel5->Enabled = rbEthernetSet->Checked;
        //eGateway->Enabled = rbEthernetSet->Checked;
        //rgOperationMode->Enabled = rbEthernetSet->Checked;
        //gbServer->Enabled = rbEthernetSet->Checked;
        //laServerIP->Enabled = rbEthernetSet->Checked;
        //eServerIPAddr->Enabled = rbEthernetSet->Checked;
        //laServerDDNS->Enabled = rbEthernetSet->Checked;
        //eServerName->Enabled = rbEthernetSet->Checked;
        /*if(cbWiFiSet->Checked)
        {
            eSSID->Color = clWindow;
            eSSIDPW->Color = clWindow;
        }
        else
        {
            eSSID->Color = clWindowFrame;
            eSSIDPW->Color = clWindowFrame;
        }*/
        //if(rgOperationMode->ItemIndex == 1)
        if(rbClient->Checked)
        {
            //TntLabel8->Enabled = cbWiFiSet->Checked;
            //eServerPort->Enabled = cbWiFiSet->Checked;
            //eServerPort->Color = clWindow;
        }
        else
        {
            //eServerPort->Color = clWindowFrame;
        }
        //TntLabel11->Enabled = rbEthernetSet->Checked;
        //eInterval->Enabled = rbEthernetSet->Checked;
        //TntLabel12->Enabled = rbEthernetSet->Checked;
        TntLabel6->Enabled = cbWiFiSet->Checked;
        //cbAPMode->Enabled = cbWiFiSet->Checked;
        pnAPMode->Enabled = cbWiFiSet->Checked;
        rbSTA->Enabled = cbWiFiSet->Checked;
        rbAP->Enabled = cbWiFiSet->Checked;
        rbEth->Enabled = cbWiFiSet->Checked;
        //gbSSID->Enabled = cbWiFiSet->Checked;
        TntLabel14->Enabled = cbWiFiSet->Checked;
        eSSID->Enabled = cbWiFiSet->Checked;
        TntLabel13->Enabled = cbWiFiSet->Checked;
        eSSIDPW->Enabled = cbWiFiSet->Checked;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::btnSetupClick(TObject *Sender)
{
        FComPort = new TFormComPort(this);
        FComPort->ShowModal();
        if(ComKind == 0)
        {
            laComKind->Caption = "Serial : " + PortUse1;
            pnComKind->Caption = laComKind->Caption;
        }
        else
        {
            laComKind->Caption = "UDP : " + asNetworkName;
            pnComKind->Caption = laComKind->Caption;
        }
        if(FComPort)
        {
            delete FComPort;
            FComPort = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::ComPort1RxChar(TObject *Sender, int Count)
{
        int Size = Count;
        char *Buffer = new char[Size];
        ZeroMemory(Buffer, Size);
        ComPort1->Read(Buffer, Size);
        //if(!bSerial)
        //{
            SerialMMTimer->Enabled = false;
            if(iSerialPos == 0)
            {
                SerialMMTimer->Interval = 500;
                bSerial = true;
            }
            SerialMMTimer->Enabled = true;
            memcpy(&RedData[iSerialPos], Buffer, Size);
            int iCutSize = 222;
            if(bAddKind == 5)
                iCutSize = 222;
            else if(bAddKind == 6)
                iCutSize = 70;
            if(iSerialPos + Size >= iCutSize)
                iRemain = (iSerialPos + Size) % iCutSize;
            iSerialPos = iSerialPos + Size;
            if(iSerialPos >= iCutSize)
            {
                SerialMMTimer->Enabled = false;
                RecieveData(RedData, iSerialPos);
                //FormMain->AddLog("Serial Send Data4 : " + IntToStr(iCutSize), clBlue);
                ZeroMemory(RedData, sizeof(RedData));
                iSerialPos = 0;
                bSerial = false;
                //iSerialPos = iSerialPos % iCutSize;
                //memcpy(RedData, &Buffer[Size - iRemain], iRemain);
            }
        //}
        if(Buffer)
        {
            delete [] Buffer;
            Buffer = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::SerialMMTimerTimer(TObject *Sender)
{
        SerialMMTimer->Enabled = false;
        if(bSerial)
        {
            bSerial = false;
            RecieveData(RedData, iSerialPos);
            iSerialPos = 0;
            iRemain = 0;
            ZeroMemory(RedData, sizeof(RedData));
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BtnSaveClick(TObject *Sender)
{
        //PortUse1 = ComBox1->Text;
        PortUse1 = ComBox2->Text;
        //BaudRateUse1 = (eBaudRate)BaudBox1->ItemIndex;
        BaudRateUse1 = (eBaudRate)BaudBox2->ItemIndex;
        //FormMain->DTR1 = (eFlow)cbDTR->ItemIndex;
        //FormMain->RTS1 = (eFlow)cbRTS->ItemIndex;
        //RS485 = cbRS485->Checked;

        //UDPIPAddress = AnsiString(Edit6->Text).TrimRight();
        UDPClientPort = StrToIntDef(AnsiString(Edit7->Text).TrimRight(), 5108);
        UDPServerPort = StrToIntDef(AnsiString(Edit8->Text).TrimRight(), 5109);
        if(rbOnlyEth->Checked)
            UDPType = 1;
        else if(rbOnlyWiFi->Checked)
            UDPType = 2;
        else
            UDPType = 0;
        if(rbUDP->Checked)
        {
            ComKind = 1;
            laComKind->Caption = "UDP : " + asNetworkName;
            pnComKind->Caption = laComKind->Caption;
            if(ComPort1->Connected)
                ComClose(0);
        }
        else
        {
            ComKind = 0;
            laComKind->Caption = "Serial : " + PortUse1;
            pnComKind->Caption = laComKind->Caption;
            BPortConClick(this);
        }
}
//---------------------------------------------------------------------------

bool __fastcall TFormDABITNet::Apply()
{
        int iPort = 0;
        if(rbUDP->Checked)
        {
            //UDPIPAddress = AnsiString(Edit6->Text).TrimRight();
            UDPClientPort = StrToIntDef(AnsiString(Edit7->Text).TrimRight(), 5108);
            UDPServerPort = StrToIntDef(AnsiString(Edit8->Text).TrimRight(), 5109);
            UDPUnicast = cbUDPUnicast->Checked;
            ComKind  = 1;
            try{
                IdUDPServer->Active = true;
                pnComKind->Font->Color = clSilver;
            }catch(...){
                pnComKind->Font->Color = clRed;
            }
        }
        else
        {
            //PortUse1 = ComBox1->Text;
            FormMain->PortUse1 = ComBox2->Text;
            //BaudRateUse1 = (eBaudRate)BaudBox1->ItemIndex;
            FormMain->BaudRateUse1 = (eBaudRate)BaudBox2->ItemIndex;
            //DTR1 = (eFlow)0;
            //RTS1 = (eFlow)0;
            ComKind  = 0;
            try{
                IdUDPServer->Active = false;
            }catch(...){
            }
        }
        DNSConnect = false;
        bStopSend = false;
        /*if(ComKind == 0)
        {
            if(!ComPortSet(ComKind, AnsiString(FormMain->PortUse1).TrimRight(), FormMain->BaudRateUse1,
                cbDTR->ItemIndex, cbRTS->ItemIndex, IPAddress, iPort, true))
            {
                pnComKind->Font->Color = clRed;
                return false;
            }
            pnComKind->Font->Color = clSilver;
        }*/
        return true;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::SearchComPort()
{
        //TRegistry *reg = new TRegistry;
        //TStringList *UnKey = new TStringList;
        //ComBox1->Items->Clear();
        ComBox2->Items->Clear();
        /*reg->RootKey = HKEY_LOCAL_MACHINE;
        if(reg->OpenKey("HARDWARE\\DEVICEMAP\\SERIALCOMM",false))
        {
            reg->GetValueNames(UnKey);
            for(int j = 0; j < UnKey->Count; j++)
            {
                if(reg->ValueExists(UnKey->Strings[j]))
                {
                    ComBox1->Items->Add(reg->ReadString(UnKey->Strings[j]));
                    ComBox2->Items->Add(reg->ReadString(UnKey->Strings[j]));
                }
            }
            //ComBox1->Sorted = true;
            reg->CloseKey();
        }*/
        /*COMMCONFIG CommConfig;
        DWORD size = sizeof(CommConfig);
        char Buf[7];
        //if(UnKey->Count <= 0)
        //{
            for (int j = 1 ; j <= 40; j++)
            {
                ZeroMemory(Buf, sizeof(Buf));
                wsprintf(Buf, "COM%d", j);
                if(GetDefaultCommConfig(Buf, &CommConfig, &size))
                {
                    //ComBox1->Items->Add("COM" + IntToStr(j));
                    ComBox2->Items->Add("COM" + IntToStr(j));
                }
            }
            //ComBox1->Sorted = true;
        //}*/
        //delete reg;
        //delete UnKey;
        //ComBox1->Sorted = true;

        using namespace std;
        TRegistry *reg = new TRegistry(KEY_READ);
        TStringList *list = new TStringList;

        reg->RootKey = HKEY_LOCAL_MACHINE;
        reg->OpenKey("HARDWARE\\DEVICEMAP\\SERIALCOMM", false);
        reg->GetValueNames(list);

        for(int i = 0; i < list->Count; i++)
            ComBox2->Items->Add(reg->ReadString(list->Strings[i]));
        delete reg;
        delete list;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BtnSystemClick(TObject *Sender)
{
        HINSTANCE Ret;
        char szSystemPath[MAX_PATH];
        GetSystemDirectory(szSystemPath, MAX_PATH);
        AnsiString Path = AnsiString(szSystemPath);
        Ret = ShellExecute(0, "open", AnsiString(Path + "\\devmgmt.msc").c_str(), Path.c_str(), Path.c_str(), SW_SHOWNORMAL);
        if((int)Ret <= 32)
        {
            Ret = ShellExecute(0, "open", AnsiString(Path + "\\devmgmt.msc").c_str(), Path.c_str(), Path.c_str(), SW_SHOWNORMAL);
            if((int)Ret <= 32)
                MessageDlg("\r\nndevmgmt.msc File Not Open.\r\n", mtError, TMsgDlgButtons() << mbOK, 0);
                //FormMain->Message("\r\ndevmgmt.msc File Not Open.\r\n", FormMain->LangFile.MessageButton04, "", "", 14, 3, 1, clBlack);
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BtnNetworkAdapterClick(TObject *Sender)
{
        system("ncpa.cpl");
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BtnDB300InfoClick(TObject *Sender)
{
        if(pnInfor->Visible)
            pnInfor->Visible = false;
        else
        {
            pnInfor->Visible = true;
            tsInfor->Click();
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::ComBox1ListBoxClick(TObject *Sender)
{
        ComBox1->Text = ComBox1->Items->Strings[ComBox1->ItemIndex];
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::ComBox1DropDown(TObject *Sender)
{
        SearchComPort();
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BPortConClick(TObject *Sender)
{
        BPortCon->Enabled = false;
        BPortClose->Enabled = false;
        //FormMain->StatusBar->Panels->Items[1]->Text = "";
        //FormMain->bAlwayOpen = false;
        if(Apply())
            BPortCon->Enabled = false;
        else
            BPortCon->Enabled = true;
        BPortClose->Enabled = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BPortCloseClick(TObject *Sender)
{
        BPortCon->Enabled = false;
        BPortClose->Enabled = false;
        ComClose(ComKind);
        //FormMain->ComPort2->Close();
        //FormMain->ComPort3->Close();
        BPortCon->Enabled = true;
        BPortClose->Enabled = true;
}
//---------------------------------------------------------------------------
                                     
void __fastcall TFormDABITNet::rbUDPClick(TObject *Sender)
{
        Label1->Enabled = rbSerial->Checked;
        Label2->Enabled = rbSerial->Checked;
        Label3->Enabled = rbSerial->Checked;
        Label4->Enabled = rbSerial->Checked;
        //ComBox1->Enabled = rbSerial->Checked;
        //BaudBox1->Enabled = rbSerial->Checked;
        ComBox2->Enabled = rbSerial->Checked;
        BaudBox2->Enabled = rbSerial->Checked;
        cbDTR->Enabled = rbSerial->Checked;
        cbRTS->Enabled = rbSerial->Checked;
        BtnSystem->Enabled = rbSerial->Checked;
        //BPortClose->Enabled = rbSerial->Checked;
        //BPortCon->Enabled = rbSerial->Checked;
        cbRS485->Enabled = rbSerial->Checked;
        Label12->Enabled = rbSerial->Checked;

        laSearchTarget->Enabled = !rbSerial->Checked;
        cbSearchTarget->Enabled = !rbSerial->Checked;
        laResponseType->Enabled = !rbSerial->Checked;
        cbResponseType->Enabled = !rbSerial->Checked;
        rbBoth->Enabled = !rbSerial->Checked;
        rbOnlyEth->Enabled = !rbSerial->Checked;
        rbOnlyWiFi->Enabled = !rbSerial->Checked;
        //Label14->Enabled = !rbSerial->Checked;
        Label15->Enabled = !rbSerial->Checked;
        Label16->Enabled = !rbSerial->Checked;
        cbUDPUnicast->Enabled = !rbSerial->Checked;
        BtnNetworkAdapter->Enabled = !rbSerial->Checked;
        //Edit6->Enabled = !rbSerial->Checked;
        //Edit7->Enabled = !rbSerial->Checked;
        //Edit8->Enabled = !rbSerial->Checked;
        BPortCon->Enabled = rbSerial->Checked;
        BPortClose->Enabled = rbSerial->Checked;
        pnComKind->Font->Color = clSilver;
        if(rbSerial->Checked)
        {
            ComKind = 0;
            //ComBox1->Color = clWindow;
            //BaudBox1->Color = clWindow;
            //ComBox2->Color = clWindow;
            //BaudBox2->Color = clWindow;
            //Edit7->Color = clWindowFrame;
            //Edit8->Color = clWindowFrame;
        }
        else
        {
            ComKind = 1;
            //ComBox1->Color = clWindowFrame;
            //BaudBox1->Color = clWindowFrame;
            //ComBox2->Color = clWindowFrame;
            //BaudBox2->Color = clWindowFrame;
            //Edit7->Color = clWindow;
            //Edit8->Color = clWindow;
        }
        if(bClick)
            BtnSave->Click();
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::tsNetworkClick(TObject *Sender)
{
        if(Sender == tsWiFi)
        {
            tnWiFi->Visible = true;
            tsWiFi->Flat = true;
            tnNetwork->Visible = false;
            tsNetwork->Flat = false;
            tnOption->Visible = false;
            tsOption->Flat = false;
            tnInfor->Visible = false;
            tsInfor->Flat = false;
            pnWiFi->Color = CaptionPanelColor;
            tsWiFi->Font->Color = TextColor;
            pnNetwork->Color = MainColor;
            tsNetwork->Font->Color = TextBGColor;
            pnOption->Color = MainColor;
            tsOption->Font->Color = TextBGColor;
            pnInfor->Color = MainColor;
            tsInfor->Font->Color = TextBGColor;
            DabitNetTap = 1;
        }
        else if(Sender == tsOption)
        {
            tnOption->Visible = true;
            tsOption->Flat = true;
            tnNetwork->Visible = false;
            tsNetwork->Flat = false;
            tnWiFi->Visible = false;
            tsWiFi->Flat = false;
            tnInfor->Visible = false;
            tsInfor->Flat = false;
            pnOption->Color = CaptionPanelColor;
            tsOption->Font->Color = TextColor;
            pnNetwork->Color = MainColor;
            tsNetwork->Font->Color = TextBGColor;
            pnWiFi->Color = MainColor;
            tsWiFi->Font->Color = TextBGColor;
            pnInfor->Color = MainColor;
            tsInfor->Font->Color = TextBGColor;
            DabitNetTap = 2;
        }
        else if(Sender == tsInfor)
        {
            tnInfor->Visible = true;
            tsInfor->Flat = true;
            tnNetwork->Visible = false;
            tsNetwork->Flat = false;
            tnWiFi->Visible = false;
            tsWiFi->Flat = false;
            tnOption->Visible = false;
            tsOption->Flat = false;
            pnInfor->Color = CaptionPanelColor;
            tsInfor->Font->Color = TextColor;
            pnNetwork->Color = MainColor;
            tsNetwork->Font->Color = TextBGColor;
            pnWiFi->Color = MainColor;
            tsWiFi->Font->Color = TextBGColor;
            pnOption->Color = MainColor;
            tsOption->Font->Color = TextBGColor;
            DabitNetTap = 3;
        }
        else
        {
            tnOption->Visible = false;
            tsOption->Flat = false;
            tnNetwork->Visible = true;
            tsNetwork->Flat = true;
            tnWiFi->Visible = false;
            tsWiFi->Flat = false;
            tnInfor->Visible = false;
            tsInfor->Flat = false;
            pnNetwork->Color = CaptionPanelColor;
            tsNetwork->Font->Color = TextColor;
            pnWiFi->Color = MainColor;
            tsWiFi->Font->Color = TextBGColor;
            pnOption->Color = MainColor;
            tsOption->Font->Color = TextBGColor;
            pnInfor->Color = MainColor;
            tsInfor->Font->Color = TextBGColor;
            DabitNetTap = 0;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::pnCaptionMouseDown(TObject *Sender,
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

void __fastcall TFormDABITNet::pnCaptionMouseUp(TObject *Sender,
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

void __fastcall TFormDABITNet::pnCaptionMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
        if(bCaptionMove)
        {
            this->Left = this->Left + (X - OldXPos);
            this->Top = this->Top + (Y - OldYPos);
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::btnHideClick(TObject *Sender)
{
        this->WindowState = wsMinimized;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::btnMaxClick(TObject *Sender)
{
        if(this->WindowState == wsMaximized)
            this->WindowState = wsNormal;
        else
            this->WindowState = wsMaximized;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::btnExitClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::rbAPModeClick(TObject *Sender)
{
        if(rbAP->Checked)
        {
            eSSID->Width = eSSIDPW->Width - 24;
            eSSID->Left = eSSIDPW->Left + 24;
            TntLabel15->Visible = true;
        }
        else
        {
            eSSID->Left = eSSIDPW->Left;
            eSSID->Width = eSSIDPW->Width;
            TntLabel15->Visible = false;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::rgUDPModeClick(TObject *Sender)
{
        if(rbOnlyWiFi->Checked)
            Edit7->Text = "5107";
        //else if(rbBoth->Checked)
        //    Edit7->Text = "5108";
        else
            Edit7->Text = "5108";
        if(bClick)
            BtnSave->Click();
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::cbSearchTypeClick(TObject *Sender)
{
        if(cbSearchTarget->ItemIndex == 1)
            rbOnlyEth->Checked = true;
        else if(cbSearchTarget->ItemIndex == 2)
            rbOnlyWiFi->Checked = true;
        else
            rbBoth->Checked = true;
        rgUDPModeClick(this);
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::cbResponseTypeClick(TObject *Sender)
{
        if(cbResponseType->ItemIndex == 1)
            cbUDPUnicast->Checked = true;
        else
            cbUDPUnicast->Checked = false;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::cbDB300TabClick(TObject *Sender)
{
        if(cbDB300Tab->ItemIndex == 1)
        {
            pnInfor->Visible = true;
            tsInfor->Click();
        }
        else
            pnInfor->Visible = false;
        DB300Visible = pnInfor->Visible;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BaudBox2Click(TObject *Sender)
{
        if(bClick)
            BtnSave->Click();
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::ComBox2Click(TObject *Sender)
{
        pnComKind->Font->Color = clSilver;
        if(bClick)
            BtnSave->Click();
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::laHelpClick(TObject *Sender)
{
        AnsiString asLink = "http://www.dabitsol.com/files/DABITNet%20Guide.pdf";
        try{
            ShellExecute(0, "open", asLink.c_str(), "", "", SW_SHOWNORMAL);
        }catch(...){
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::laHelpMouseDown(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
       laHelp->Font->Style = TFontStyles() << fsItalic << fsUnderline << fsBold;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::laHelpMouseUp(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
       laHelp->Font->Style = TFontStyles() << fsUnderline << fsBold;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::btnHelpClick(TObject *Sender)
{
        AnsiString asLink = "http://www.dabitsol.com/files/DABITNet%20Guide.pdf";
        try{
            ShellExecute(0, "open", asLink.c_str(), "", "", SW_SHOWNORMAL);
        }catch(...){
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::cbUDPUnicastClick(TObject *Sender)
{
        UDPUnicast = cbUDPUnicast->Checked;
}
//---------------------------------------------------------------------------

void __fastcall TFormDABITNet::BtnDeaultIPClick(TObject *Sender)
{
        eMemo->Text = DefaultName;
        eIPAddr->Text = DefaultIP;
        ePort->Text = IntToStr(DefaultPort);
        eSubnetMask->Text = DefaultSubnetMask;
        eGateway->Text = DefaultGateway;
        eServerIPAddr->Text = DefaultServerIP;
        eServerPort->Text = IntToStr(DefaultServerPort);
}
//---------------------------------------------------------------------------

