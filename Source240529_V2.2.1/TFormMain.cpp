//---------------------------------------------------------------------------

#include <vcl.h>
#pragma hdrstop

#include "TFormMain.h"
#include <IniFiles.hpp>
#include <windows.h>
#include <iphlpapi.h>
#include <tlhelp32.h>
//---------------------------------------------------------------------------
#pragma package(smart_init)
#pragma link "CPort"
#pragma link "iphlpapi.lib"
#pragma link "te_controls"
#pragma link "TntComCtrls"
#pragma link "TntStdCtrls"
#pragma link "TntButtons"
#pragma link "TntExtCtrls"
#pragma link "SUIURLLabel"
#pragma link "HanConvert"
#pragma link "MMTimer"
#pragma resource "*.dfm"
TFormMain *FormMain;
//---------------------------------------------------------------------------

__fastcall TFormMain::TFormMain(TComponent* Owner)
        : TForm(Owner)
{
    //initialize()와 같은 역할(초기값세팅 및 UI만들기)
        // 처음 시작시
        BasePath = IncludeTrailingPathDelimiter(ExtractFilePath(Application->ExeName));
        SetCurrentDir(BasePath);
        DestSysPath = "Sys";//목적지 시스템 경로
        if(!DirectoryExists(BasePath + DestSysPath))
            CreateDir(BasePath + DestSysPath);
        DestLogPath = "Log";//목적지 로그 경로
        if(!DirectoryExists(BasePath + DestLogPath))
            CreateDir(BasePath + DestLogPath);
        DestFontPath = IncludeTrailingPathDelimiter(DestSysPath) + "Font";
        if(!DirectoryExists(BasePath + DestFontPath))
            CreateDir(BasePath + DestFontPath);
        DestFirmwarePath = "Firmware";
        if(!DirectoryExists(BasePath + DestFirmwarePath))
            CreateDir(BasePath + DestFirmwarePath);
        bStopSend = false;
        bMsg = true;
        ImageSendingBlockCount = 0;
        RetryCnt = 0;
        //MainColor = (TColor)RGB(10, 10, 10);
        MainColor = clBtnFace;//(TColor)RGB(206, 206, 206);
        ButtonColor = clBtnFace;//(TColor)RGB(56, 56, 56);
        TextBGColor = (TColor)RGB(10, 10, 10);//(TColor)RGB(215, 215, 215);
        //TextColor = (TColor)RGB(215, 215, 215);
        TextColor = (TColor)RGB(10, 10, 10);
        MainDivideColor = (TColor)RGB(113, 113, 113);
        MainRectangleColor = (TColor)RGB(165, 165, 165);//(TColor)RGB(32, 32, 32);
        CaptionPanelColor = (TColor)RGB(125,154,158);
        CaptionTextColor = (TColor)RGB(245, 245, 245);
        MainFont = "맑은 고딕";
        MainFontSize = 9;
        this->Color = MainColor;
        pnMain->Color = MainColor;
        tnEnv->Color = MainColor;
        tnASCIIMessage->Color = MainColor;
        tnSpecial->Color = MainColor;
        tnInfor->Color = MainColor;
        pnMainDivide->Color = MainDivideColor;
        pnDivide->Color = MainDivideColor;
        pnDivide2->Color = MainDivideColor;
        pnASCII->Color = MainColor;
        pntsButton->Color = MainColor;//CaptionPanelColor;//ButtonColor;
        pnTapInfor->Color = MainColor;
        pnICON->Color = MainColor;//CaptionPanelColor;//ButtonColor;
        tnSignAddr->Color = MainColor;
        pnCaption->Color = MainDivideColor;//ButtonColor;
        pnBorderIcon->Color = MainDivideColor;//ButtonColor;
        pnCaption->Font->Color = CaptionTextColor;
        pnCaption->Font->Name = MainFont;
        pnCaption->Font->Size = 10;//MainFontSize;
        rgPageNo->Color = MainRectangleColor;
        pnPageNo->Color = MainColor;
        rbPageNo1->Color = MainColor;
        rbPageNo1->Font->Color = TextColor;
        rbPageNo1->Font->Name = MainFont;
        rbPageNo1->Font->Size = MainFontSize;
        rbPageNo2->Color = MainColor;
        rbPageNo2->Font->Color = TextColor;
        rbPageNo2->Font->Name = MainFont;
        rbPageNo2->Font->Size = MainFontSize;
        rbTextNo->Color = MainRectangleColor;
        pnTextNo->Color = MainColor;
        rbNo1->Color = MainColor;
        rbNo1->Font->Color = TextColor;
        rbNo1->Font->Name = MainFont;
        rbNo1->Font->Size = MainFontSize;
        rbNo2->Color = MainColor;
        rbNo2->Font->Color = TextColor;
        rbNo2->Font->Name = MainFont;
        rbNo2->Font->Size = MainFontSize;
        rbNo3->Color = MainColor;
        rbNo3->Font->Color = TextColor;
        rbNo3->Font->Name = MainFont;
        rbNo3->Font->Size = MainFontSize;
        cbPageNo->Font->Name = MainFont;
        cbPageNo->Font->Size = MainFontSize;
        cbDispControl->Font->Name = MainFont;
        cbDispControl->Font->Size = MainFontSize;
        cbDispType->Font->Name = MainFont;
        cbDispType->Font->Size = MainFontSize;
        cbCodeKind->Font->Name = MainFont;
        cbCodeKind->Font->Size = MainFontSize;
        cbFontSize->Font->Name = MainFont;
        cbFontSize->Font->Size = MainFontSize;
        cbEntEffect->Font->Name = MainFont;
        cbEntEffect->Font->Size = MainFontSize;
        cbLasEffect->Font->Name = MainFont;
        cbLasEffect->Font->Size = MainFontSize;
        cbAssistEffect->Font->Name = MainFont;
        cbAssistEffect->Font->Size = MainFontSize;
        cbSpeed->Font->Name = MainFont;
        cbSpeed->Font->Size = MainFontSize;
        cbFontKind->Font->Name = MainFont;
        cbFontKind->Font->Size = MainFontSize;
        cbEntDirect->Font->Name = MainFont;
        cbEntDirect->Font->Size = MainFontSize;
        cbLasDirect->Font->Name = MainFont;
        cbLasDirect->Font->Size = MainFontSize;
        cbDelay->Font->Name = MainFont;
        cbDelay->Font->Size = MainFontSize;
        cbStartXPos->Font->Name = MainFont;
        cbStartXPos->Font->Size = MainFontSize;
        cbStartYPos->Font->Name = MainFont;
        cbStartYPos->Font->Size = MainFontSize;
        cbEndXPos->Font->Name = MainFont;
        cbEndXPos->Font->Size = MainFontSize;
        cbEndYPos->Font->Name = MainFont;
        cbEndYPos->Font->Size = MainFontSize;
        cbBGImage->Font->Name = MainFont;
        cbBGImage->Font->Size = MainFontSize;
        cbDIBDAddress->Font->Name = MainFont;
        cbDIBDAddress->Font->Size = MainFontSize;
        cbWaitTime->Font->Name = MainFont;
        cbWaitTime->Font->Size = MainFontSize;
        TTntSpeedButton *BtnSave;
        TTntPanel *pnBtnButton;
        TTntRichEdit *tMemo;
        TTntLabel *tLabel;
        try{
            for(int i = 0; i < 10; i++)
            {
                BtnSave = dynamic_cast<TTntSpeedButton *>(FindComponent("btnSave" + IntToStr(i + 1)));
                if(BtnSave)
                {
                    BtnSave->Font->Color = TextBGColor;
                    BtnSave->Font->Name = MainFont;
                    BtnSave->Font->Size = MainFontSize;
                }
                pnBtnButton = dynamic_cast<TTntPanel *>(FindComponent("pnbtnSave" + IntToStr(i + 1)));
                if(pnBtnButton)
                    pnBtnButton->Color = ButtonColor;
                BtnSave = dynamic_cast<TTntSpeedButton *>(FindComponent("ASCIISend" + IntToStr(i + 1)));
                if(BtnSave)
                {
                    BtnSave->Font->Color = TextBGColor;
                    BtnSave->Font->Name = MainFont;
                    BtnSave->Font->Size = MainFontSize;
                }
                pnBtnButton = dynamic_cast<TTntPanel *>(FindComponent("pnASCIISend" + IntToStr(i + 1)));
                if(pnBtnButton)
                    pnBtnButton->Color = ButtonColor;
                tMemo = dynamic_cast<TTntRichEdit*>(FindComponent("mASCIIText" + IntToStr(i + 1)));
                if(tMemo)
                {
                    tMemo->Color = TextBGColor;
                    tMemo->Font->Color = CaptionTextColor;
                    //tMemo->Color = (TColor)RGB(245, 245, 245);
                    tMemo->Font->Name = MainFont;
                    tMemo->Font->Size = 11;
                }
            }
            for(int i = 0; i < 24; i++)
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
        }
        //eColor->Color = TextBGColor;
        //eBGColor->Color = TextBGColor;
        //eData->Color = TextBGColor;
        pnLogo->Color = TextBGColor;
        eColor->Color = TextColor;
        eColor->Font->Color = CaptionTextColor;//TextColor;
        eColor->Font->Name = MainFont;
        eColor->Font->Size = 13;
        eBGColor->Color = TextColor;
        eBGColor->Font->Color = CaptionTextColor;//TextColor;
        eBGColor->Font->Name = MainFont;
        eBGColor->Font->Size = 13;
        eData->Color = TextColor;
        eData->Font->Color = CaptionTextColor;//TextColor;
        eData->Font->Name = MainFont;
        eData->Font->Size = 13;
        tsASCIIMessage->Font->Color = TextColor;
        tsASCIIMessage->Font->Name = MainFont;
        tsASCIIMessage->Font->Size = 10;
        tsEnv->Font->Color = TextColor;
        tsEnv->Font->Name = MainFont;
        tsEnv->Font->Size = 10;
        tsSpecial->Font->Color = TextColor;
        tsSpecial->Font->Name = MainFont;
        tsSpecial->Font->Size = 10;
        tsInfor->Font->Color = TextColor;
        tsInfor->Font->Name = MainFont;
        tsInfor->Font->Size = 10;
        laDefault->Font->Color = TextColor;
        laDefault->Font->Name = MainFont;
        laDefault->Font->Size = MainFontSize;
        laAddress->Font->Color = TextColor;
        laAddress->Font->Name = MainFont;
        laAddress->Font->Size = MainFontSize;
        DataSend->Font->Color = TextBGColor;
        DataSend->Font->Name = MainFont;
        DataSend->Font->Size = MainFontSize;
        pnDataSend->Color = ButtonColor;
        BtnFactoryReset->Font->Color = TextBGColor;
        BtnFactoryReset->Font->Name = MainFont;
        BtnFactoryReset->Font->Size = MainFontSize;
        pnBtnFactoryReset->Color = ButtonColor;
        BtnMsgErase->Font->Color = TextBGColor;
        BtnMsgErase->Font->Name = MainFont;
        BtnMsgErase->Font->Size = MainFontSize;
        pnBtnMsgErase->Color = ButtonColor;
        BtnPacketRead->Font->Color = TextBGColor;
        BtnPacketRead->Font->Name = MainFont;
        BtnPacketRead->Font->Size = MainFontSize;
        pnBtnPacketRead->Color = ButtonColor;

        gbSignSize->Color = MainRectangleColor;
        pnSignSize->Color = MainColor;
        //gbInfor->Color = MainRectangleColor;
        //pnInfor->Color = MainColor;
        gbProtocol->Color = MainRectangleColor;
        pnProtocol->Color = MainColor;
        rgASCII->Color = MainColor;
        rgHex->Color = MainColor;
        laSignSize->Color = MainColor;
        laSignSize->Font->Color = TextColor;
        laSignSize->Font->Name = MainFont;
        laSignSize->Font->Size = MainFontSize;
        laSettings->Color = MainColor;
        laSettings->Font->Color = TextColor;
        laSettings->Font->Name = MainFont;
        laSettings->Font->Size = MainFontSize;
        laProgram->Color = MainColor;
        laProgram->Font->Color = TextColor;
        laProgram->Font->Name = MainFont;
        laProgram->Font->Size = MainFontSize;
        laProtocol->Font->Color = TextColor;
        laProtocol->Font->Name = MainFont;
        laProtocol->Font->Size = MainFontSize;
        rgASCII->Font->Color = TextColor;
        rgASCII->Font->Name = MainFont;
        rgASCII->Font->Size = MainFontSize;
        rgHex->Font->Color = TextColor;
        rgHex->Font->Name = MainFont;
        rgHex->Font->Size = MainFontSize;
        TntEdit1->Font->Name = MainFont;
        TntEdit1->Font->Size = MainFontSize;
        TntEdit2->Font->Name = MainFont;
        TntEdit2->Font->Size = MainFontSize;
        cbProtocol->Font->Name = MainFont;
        cbProtocol->Font->Size = MainFontSize;
        cbProgramType->Font->Name = MainFont;
        cbProgramType->Font->Size = MainFontSize;
        cbDirection->Font->Name = MainFont;
        cbDirection->Font->Size = MainFontSize;
        cbWaitTime->Font->Name = MainFont;
        cbWaitTime->Font->Size = MainFontSize;

        TTntLabel *Label;
        for(int i = 0; i < 13; i++)
        {
            Label = dynamic_cast<TTntLabel*>(FindComponent("TntLabel" + IntToStr(i + 1)));
            if(Label)
            {
                Label->Font->Color = TextColor;
                Label->Font->Name = MainFont;
                Label->Font->Size = MainFontSize;
            }
        }
        BtnSignSize->Font->Color = TextBGColor;
        BtnSignSize->Font->Name = MainFont;
        BtnSignSize->Font->Size = MainFontSize;
        pnBtnSignSize->Color = ButtonColor;
        gbSettings->Color = MainRectangleColor;
        pnSettings->Color = ButtonColor;
        BtnFontSend->Font->Color = TextBGColor;
        BtnFontSend->Font->Name = MainFont;
        BtnFontSend->Font->Size = MainFontSize;
        pnBtnFontSend->Color = ButtonColor;
        BtnLEDSetting->Font->Color = TextBGColor;
        BtnLEDSetting->Font->Name = MainFont;
        BtnLEDSetting->Font->Size = MainFontSize;
        pnBtnLEDSetting->Color = ButtonColor;
        BtnDIBDDownload->Font->Color = TextBGColor;
        BtnDIBDDownload->Font->Name = MainFont;
        BtnDIBDDownload->Font->Size = MainFontSize;
        pnBtnDIBDDownload->Color = ButtonColor;
        BtnDABITOption->Font->Color = TextBGColor;
        BtnDABITOption->Font->Name = MainFont;
        BtnDABITOption->Font->Size = MainFontSize;
        pnBtnDABITOption->Color = ButtonColor;
        BtnETC->Font->Color = TextBGColor;
        BtnETC->Font->Name = MainFont;
        BtnETC->Font->Size = MainFontSize;
        pnBtnETC->Color = ButtonColor;
        BtnLog->Font->Color = TextBGColor;
        BtnLog->Font->Name = MainFont;
        BtnLog->Font->Size = MainFontSize;
        pnBtnLog->Color = ButtonColor;
        BtnComPort->Font->Color = clNavy;//TextBGColor;
        BtnComPort->Font->Name = MainFont;
        BtnComPort->Font->Size = MainFontSize;
        pnBtnComPort->Color = ButtonColor;
        BtnApply->Font->Color = TextBGColor;
        BtnApply->Font->Name = MainFont;
        BtnApply->Font->Size = MainFontSize;
        pnBtnApply->Color = ButtonColor;
        laLanguage->Font->Color = TextColor;
        laLanguage->Font->Name = MainFont;
        laLanguage->Font->Size = MainFontSize;
        cbLanguage->Font->Name = MainFont;
        cbLanguage->Font->Size = MainFontSize;

        pnMemInput->Color = MainColor;
        laMemInput->Color = MainColor;
        laMemInput->Font->Color = TextColor;
        laMemInput->Font->Name = MainFont;
        laMemInput->Font->Size = MainFontSize;
        cbMsgInput->Font->Name = MainFont;
        cbMsgInput->Font->Size = MainFontSize;
        BtnMemInput->Font->Color = TextBGColor;
        BtnMemInput->Font->Name = MainFont;
        BtnMemInput->Font->Size = MainFontSize;
        pnBtnMemInput->Color = ButtonColor;
        gbMemInput->Color = MainRectangleColor;

        cbMsgMemClear->Font->Name = MainFont;
        cbMsgMemClear->Font->Size = MainFontSize;
        BtnMsgErase->Font->Color = TextBGColor;
        BtnMsgErase->Font->Name = MainFont;
        BtnMsgErase->Font->Size = MainFontSize;
        pnBtnMsgErase->Color = ButtonColor;

        laBGImage->Color = MainColor;
        pnBGImage->Color = MainColor;
        gbBGImage->Color = MainRectangleColor;
        laBGImage->Font->Color = TextColor;
        laBGImage->Font->Name = MainFont;
        laBGImage->Font->Size = MainFontSize;
        cbBGISelect->Font->Name = MainFont;
        cbBGISelect->Font->Size = MainFontSize;
        BtnBGImage->Font->Color = TextBGColor;
        BtnBGImage->Font->Name = MainFont;
        BtnBGImage->Font->Size = MainFontSize;
        pnBtnBGImage->Color = ButtonColor;

        laSync->Color = MainColor;
        pnSync->Color = MainColor;
        gbSync->Color = MainRectangleColor;
        laSync->Font->Color = TextColor;
        laSync->Font->Name = MainFont;
        laSync->Font->Size = MainFontSize;
        cbSoundSec1->Font->Name = MainFont;
        cbSoundSec1->Font->Size = MainFontSize;
        cbSoundSec2->Font->Name = MainFont;
        cbSoundSec2->Font->Size = MainFontSize;
        cbSoundSec3->Font->Name = MainFont;
        cbSoundSec3->Font->Size = MainFontSize;
        cbSoundSec4->Font->Name = MainFont;
        cbSoundSec4->Font->Size = MainFontSize;
        SLabel1->Font->Color = TextColor;
        SLabel1->Font->Name = MainFont;
        SLabel1->Font->Size = MainFontSize;
        SLabel2->Font->Color = TextColor;
        SLabel2->Font->Name = MainFont;
        SLabel2->Font->Size = MainFontSize;
        SLabel3->Font->Color = TextColor;
        SLabel3->Font->Name = MainFont;
        SLabel3->Font->Size = MainFontSize;
        SLabel4->Font->Color = TextColor;
        SLabel4->Font->Name = MainFont;
        SLabel4->Font->Size = MainFontSize;
        BtnSendSync->Font->Color = TextBGColor;
        BtnSendSync->Font->Name = MainFont;
        BtnSendSync->Font->Size = MainFontSize;
        pnBtnSendSync->Color = ButtonColor;

        laBright->Color = MainColor;
        pnBright->Color = MainColor;
        gbBright->Color = MainRectangleColor;
        laBright->Font->Color = TextColor;
        laBright->Font->Name = MainFont;
        laBright->Font->Size = MainFontSize;
        cbBright->Font->Name = MainFont;
        cbBright->Font->Size = MainFontSize;
        BtnBright->Font->Color = TextBGColor;
        BtnBright->Font->Name = MainFont;
        BtnBright->Font->Size = MainFontSize;
        pnBtnBright->Color = ButtonColor;
        laScreen->Font->Color = TextColor;
        laScreen->Font->Name = MainFont;
        laScreen->Font->Size = MainFontSize;
        cbScreen->Font->Name = MainFont;
        cbScreen->Font->Size = MainFontSize;
        BtnScreen->Font->Color = TextBGColor;
        BtnScreen->Font->Name = MainFont;
        BtnScreen->Font->Size = MainFontSize;
        pnBtnScreen->Color = ButtonColor;
        laRealTime->Font->Color = TextColor;
        laRealTime->Font->Name = MainFont;
        laRealTime->Font->Size = MainFontSize;
        cbRealTime->Font->Name = MainFont;
        cbRealTime->Font->Size = MainFontSize;
        BtnRealTime->Font->Color = TextBGColor;
        BtnRealTime->Font->Name = MainFont;
        BtnRealTime->Font->Size = MainFontSize;
        pnBtnRealTime->Color = ButtonColor;

        BitBtnPowerLEDOn->Font->Color = TextBGColor;
        BitBtnPowerLEDOn->Font->Name = MainFont;
        BitBtnPowerLEDOn->Font->Size = MainFontSize;
        pnBitBtnPowerLEDOn->Color = ButtonColor;
        BitBtnPowerLEDOff->Font->Color = TextBGColor;
        BitBtnPowerLEDOff->Font->Name = MainFont;
        BitBtnPowerLEDOff->Font->Size = MainFontSize;
        pnBitBtnPowerLEDOff->Color = ButtonColor;
        Label25->Color = TextBGColor;//MainColor;
        Label25->Font->Color = CaptionTextColor;
        Label25->Font->Name = MainFont;
        //Label25->Font->Size = MainFontSize;
        BtnTimeRead->Font->Color = TextBGColor;
        BtnTimeRead->Font->Name = MainFont;
        BtnTimeRead->Font->Size = MainFontSize;
        pnBtnTimeRead->Color = ButtonColor;
        BtnTimeSet->Font->Color = TextBGColor;
        BtnTimeSet->Font->Name = MainFont;
        BtnTimeSet->Font->Size = MainFontSize;
        pnBtnTimeSet->Color = ButtonColor;
        BtnReset->Font->Color = TextBGColor;
        BtnReset->Font->Name = MainFont;
        BtnReset->Font->Size = MainFontSize;
        pnBtnReset->Color = ButtonColor;
        BtnFReset->Font->Color = TextBGColor;
        BtnFReset->Font->Name = MainFont;
        BtnFReset->Font->Size = MainFontSize;
        pnBtnFReset->Color = ButtonColor;
        BtnMsgMemClear->Font->Color = TextBGColor;
        BtnMsgMemClear->Font->Name = MainFont;
        BtnMsgMemClear->Font->Size = MainFontSize;
        pnBtnMsgMemClear->Color = ButtonColor;

        SpeedButtonPreview->Font->Color = TextBGColor;
        SpeedButtonPreview->Font->Name = MainFont;
        SpeedButtonPreview->Font->Size = MainFontSize;
        pnSpeedButtonPreview->Color = ButtonColor;
        SBASCIIPreview->Font->Color = TextBGColor;
        SBASCIIPreview->Font->Name = MainFont;
        SBASCIIPreview->Font->Size = MainFontSize;
        pnSBASCIIPreview->Color = ButtonColor;
        laAddress->Font->Color = TextColor;
        laAddress->Font->Name = MainFont;
        laAddress->Font->Size = MainFontSize;
        TntLabel8->Font->Color = TextColor;
        TntLabel8->Font->Name = MainFont;
        TntLabel8->Font->Size = MainFontSize;
        TntLabel9->Font->Color = TextColor;
        TntLabel9->Font->Name = MainFont;
        TntLabel9->Font->Size = MainFontSize;

        pnLog->Color = MainColor;
        pnLogCaption->Color = CaptionPanelColor;//MainDivideColor;
        pnLogCaption->Font->Color = TextColor;//TextBGColor;
        pnLogCaption->Font->Name = MainFont;
        pnLogCaption->Font->Size = 10;//MainFontSize;
        pnBtnLogButton->Color = CaptionPanelColor;//MainDivideColor;
        RichEditLog->Font->Name = MainFont;
        RichEditLog->Font->Size = MainFontSize;
        bTextKeyDown = false;
}

//여기까장 초기값 세팅(버튼 만들고, 선언하고, 초기값 넣어주고 등등)
//---------------------------------------------------------------------------

void __fastcall TFormMain::FormCreate(TObject *Sender)
{
        // 폼 생성시
        AnsiString mm = VersionInfo("FileVersion");
        TStringList *nn = new TStringList();
        this->DoubleBuffered = true;
        ExtractStrings(TSysCharSet()<<'.', TSysCharSet(), mm.c_str(), nn);
        for(int i=0; i < nn->Count; i++)
            VerMj[i] = (nn->Strings[i]);
        if(nn)
        {
            delete nn;
            nn = NULL;
        }
        this->Caption = "DABIT Protocol Simulator(V" + FormMain->VerMj[0] + "." + FormMain->VerMj[1] + "." + FormMain->VerMj[2] + ")";//8.00)";
        //pnCaption->Caption = "       " + this->Caption;
        pnCaption->Caption = "  " + this->Caption;
        RoadIniFile();
        cbLanguage->ItemIndex = iLanguage;
        RoadLangFile(cbLanguage->Text);
        PreviewRoadIniFile();
        try{
            UpDownHeight->Position = ModuleHeight;
            UpDownWidth->Position = ModuleWidth;
            if(ProgramType == e2BPP)
                cbProgramType->ItemIndex = 0;
            else if(ProgramType == e24BPP)
                cbProgramType->ItemIndex = 3;
            else if(ProgramType == e8BPP)
                cbProgramType->ItemIndex = 2;
            //else if(ProgramType == e16BPP)
            //    cbProgramType->ItemIndex = 3;
            else
                cbProgramType->ItemIndex = 1;
            cbDirection->Clear();
            cbDirection->Items->Add(LangFile.FormProtocolDirectItem1);
            cbDirection->Items->Add(LangFile.FormProtocolDirectItem2);
            cbDirection->Items->Add(LangFile.FormProtocolDirectItem3);
            cbDirection->Items->Add(LangFile.FormProtocolDirectItem4);
            cbDirection->Items->Add(LangFile.FormProtocolDirectItem5);
            cbDirection->Items->Add(LangFile.FormProtocolDirectItem6);
            cbDirection->ItemIndex = Direct;
            cbDirectCheck->Checked = DirectCheck;
            cbDirectCheckClick(this);
            InputLang();
            rbTextNoRoadClick(this);
            if(PorotocolType == 0)
                cbProtocol->ItemIndex = 1;
            else
                cbProtocol->ItemIndex = 0;
            //if(PorotocolType == 1)
            //    rgASCII->Checked = true;
            //else
            //    rgHex->Checked = true;
            if(ProtocolPageNo >= 1)
            {
                cbPageNo->ItemIndex = ProtocolPageNo - 1;
                rbPageNo2->Checked = true;
            }
            else
                rbPageNo1->Checked = true;
            if(ProtocolBlockNo == 1)
                rbNo2->Checked = true;
            else if(ProtocolBlockNo == 2)
                rbNo3->Checked = true;
            else// if(ProtocolBlockNo == 0)
                rbNo1->Checked = true;
            rbTextNoRoadClick(this);
        }catch(...){
        }
        cbBGISelect->Clear();
        cbBGISelect->Items->Add(LangFile.FormProtocolEffect1);
        for(int i = 1; i <= 255; i++)
            cbBGISelect->Items->Add(IntToStr(i));
        cbBGISelect->ItemIndex = 0;
        int PageIndex = ProtocolPageNo;
        cbMsgInput->ItemIndex = MessageCount - 1;
        if(cbMsgInput->ItemIndex <= 0)
            cbMsgInput->ItemIndex = 0;
        ProtocolPageNo = PageIndex;
        cbMsgMemClear->ItemIndex = 0;
        cbSoundSec1->ItemIndex = 0;
        cbSoundSec2->ItemIndex = 0;
        cbSoundSec3->ItemIndex = 0;
        cbSoundSec4->ItemIndex = 0;
        cbBright->ItemIndex = 0;

        TDateTime CurTime;
        CurTime = Now();
        Label25->Caption = FormatDateTime("yy-mm-dd '('", Now()) + GetDay(ReturnDay(CurTime)) + ") ";
        Label25->Caption = Label25->Caption + FormatDateTime("hh:nn:ss", Now());

        int tempIndex = Controler1;
        cbDIBDAddress->Clear();
        char Buf[3];
        for(int i = 0; i <= 31; i++)
        {
            ZeroMemory(Buf, 3);
            wsprintf(Buf, "%02d", i);
            cbDIBDAddress->Items->Add("DABIT " + AnsiString(Buf));
        }
        Controler1 = tempIndex;
        cbDIBDAddress->ItemIndex = Controler1;
        AnsiString ASCIIMessageFileName = BasePath + IncludeTrailingPathDelimiter(DestSysPath) + "ASCIIMessageData.dat";
        ZeroMemory(&ASCIIMessageData, sizeof(ASCIIMessageData));
        cbWaitTime->ItemIndex = (WaitTime / 1000) - 1;
        if(cbWaitTime->ItemIndex < 0)
            cbWaitTime->ItemIndex = 3;
        TFileStream *ASCIIMessageFile = NULL;
        AnsiString asMessage = "";
        int iDataCount = 0;
        if(FileExists(ASCIIMessageFileName))
        {
            try{
                ASCIIMessageFile = new TFileStream(ASCIIMessageFileName, fmOpenRead | fmShareCompat);
                iDataCount = ASCIIMessageFile->Size / sizeof(ASCIIMessageData[0]);
                ASCIIMessageFile->Read(&ASCIIMessageData, sizeof(ASCIIMessageData[0]) * iDataCount);
            }catch(...){
            }
            if(ASCIIMessageFile)
            {
                delete ASCIIMessageFile;
                ASCIIMessageFile = NULL;
            }
            //for(int i = 0; i < iDataCount; i++)
            //{
            //    asMessage = AnsiString((char *)ASCIIMessageData[i].MessageData).SubString(1, 20);
            //    lbMesageList->Items->Add(asMessage);
            //}
        }
        else
        {
            asMessage = "![000Hello world!]";
            memcpy(&ASCIIMessageData[iDataCount++].MessageData, asMessage.c_str(), asMessage.Length());
            asMessage = "![000/C1Hello /C2World!]";
            memcpy(&ASCIIMessageData[iDataCount++].MessageData, asMessage.c_str(), asMessage.Length());
            asMessage = "![0001234!]";
            memcpy(&ASCIIMessageData[iDataCount++].MessageData, asMessage.c_str(), asMessage.Length());
            asMessage = "![0001234!]33";
            memcpy(&ASCIIMessageData[iDataCount++].MessageData, asMessage.c_str(), asMessage.Length());
            asMessage = "![0032/P0000/D9901/F0003/E0101/S2002/X0000/Y0000/B000/C3/G0/T0!]";
            memcpy(&ASCIIMessageData[10].MessageData, asMessage.c_str(), asMessage.Length());
            iDataCount = 10;
            try{
                ASCIIMessageFile = new TFileStream(ASCIIMessageFileName, fmCreate | fmShareCompat);
                ASCIIMessageFile->Write(&ASCIIMessageData, sizeof(ASCIIMessageData[0]) * iDataCount);
            }catch(...){
            }
            if(ASCIIMessageFile)
            {
                delete ASCIIMessageFile;
                ASCIIMessageFile = NULL;
            }
            //for(int i = 0; i < iDataCount; i++)
            //{
            //    asMessage = AnsiString((char *)ASCIIMessageData[i].MessageData).SubString(1, 20);
            //    lbMesageList->Items->Add(asMessage);
            //}
        }
        try{
            TTntRichEdit *tMemo;
            for(int i = 0; i < 10; i++)
            {
                tMemo = dynamic_cast<TTntRichEdit*>(FindComponent("mASCIIText" + IntToStr(i + 1)));
                if(tMemo)
                {
                    AnsiString asData = AnsiString((char*)ASCIIMessageData[i].MessageData).SubString(1, sizeof(ASCIIMessageData[0].MessageData));
                    AnsiString EditFileName = BasePath + IncludeTrailingPathDelimiter(FormMain->DestSysPath) + "ASCIIData" + IntToStr(i + 1) + ".dat";
                    tMemo->Text = (WideString)"";
                    if(FileExists(EditFileName))
                        tMemo->Lines->LoadFromFile(EditFileName);
                    else
                        tMemo->Text = (WideString)asData;
                }
            }
        }catch(...){
        }
        if(FormMain->bOptionJ4 == 7)
        {
            cbSoundSec3->Enabled = true;
            SLabel3->Enabled = true;
            cbSoundSec4->Enabled = true;
            SLabel4->Enabled = true;
        }
        else
        {
            cbSoundSec3->Enabled = false;
            SLabel3->Enabled = false;
            cbSoundSec4->Enabled = false;
            SLabel4->Enabled = false;
        }
        bFirst = true;
        //lbMesageList->ItemIndex = 0;
        //lbMesageListClick(this);
        ShowSetting();
        this->Top = (Screen->Height - this->Height) / 2;
        if(this->Top < 0)
            this->Top = 0;
        this->Left = (Screen->Width - this->Width) / 2;
        if(this->Left < 0)
            this->Left = 0;
        this->BringToFront();
        this->Show();
        /*if(FormLog)
        {
           FormLog->Top = this->Top + this->Hegiht;
           FormLog->Left = this->Left;
           FormLog->Show();
        }*/
}

//여기까지도 초기화 UI생성
//---------------------------------------------------------------------------

void __fastcall TFormMain::FormCloseQuery(TObject *Sender, bool &CanClose)
{
        // 프로그램 종료시
        SaveIniFile();
        try{
            if(DMClient)
            {
                DMClient->ClientSocket1->Close();
                delete DMClient;
                DMClient = NULL;
            }
        }catch(...){
        }
}
//프로그램 종료하면 iniFile저장하고 소켓 닫기
//---------------------------------------------------------------------------

void __fastcall TFormMain::RoadIniFile()
{
        // 저장된 정보 ini 파일에서 불러오는 함수
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
            SectionName = "Main";
            PortUse1                 = IF->ReadString(SectionName, "PortUse1", "COM1");
            BaudRateUse1             = (eBaudRate)IF->ReadInteger(SectionName, "BaudRate1", 6);
            IPAddress                = IF->ReadString(SectionName, "IPAddress", "127.0.0.1");
            IPPort                   = IF->ReadInteger(SectionName, "IPPort", 5000);
            UDPIPAddress             = IF->ReadString(SectionName, "UDPIPAddress", "127.0.0.1");
            ServerPort               = IF->ReadInteger(SectionName, "ServerPort", 5000);
            DSPPort                  = IF->ReadInteger(SectionName, "Main_DSP_PORT", 5108);
            SRCPort                  = 5109;//IF->ReadInteger(SectionName, "Main_SRC_PORT", 5109);
            ComKind                  = IF->ReadInteger(SectionName, "ComKind", 0);
            ModuleWidth              = IF->ReadInteger(SectionName, "ModuleWidth", 6);
            ModuleHeight             = IF->ReadInteger(SectionName, "ModuleHeight", 2);
            LanDelayTime             = IF->ReadInteger(SectionName, "LanDelayTime", 3000);
            DelayRun                 = IF->ReadBool(SectionName, "FlagReplyPacket", false);
            WaitTime                 = IF->ReadInteger(SectionName, "ReplyPacket0_DelayTime", 1000);
            DelayTime                = IF->ReadInteger(SectionName, "ReplyPacket1_DelayTime", 350);
            ServerCommOpenDelay      = IF->ReadInteger(SectionName, "SvrCommOpenDly", 5000);
            ProgramType              = IF->ReadInteger(SectionName, "ProgramType", e3BPP);
            Controler1               = IF->ReadInteger(SectionName, "DibdAddress", 0);
            FlagCrcPacket            = IF->ReadBool(SectionName, "FlagCrcPacket", false);
            FlagDlePacket            = IF->ReadBool(SectionName, "FlagDlePacket", false);
            LogFileCnt               = IF->ReadInteger(SectionName, "LogFileCnt", 1);
            OldLogDate               = IF->ReadString(SectionName, "OldLogDate", FormatDateTime("YYYYMMDD", Now()));
            DisplayType              = IF->ReadInteger(SectionName, "DisplayType", 0);
            ColorOder                = IF->ReadInteger(SectionName, "ColorOder", 0);
            ScanOder                 = IF->ReadInteger(SectionName, "ScanOder", 1);
            Bright                   = IF->ReadInteger(SectionName, "Bright", 0);
            Direct                   = IF->ReadInteger(SectionName, "Direction", 0);
            DirectCheck              = IF->ReadBool(SectionName, "DirectCheck", false);
            PorotocolType            = IF->ReadInteger(SectionName, "PorotocolType", 0);
            LastFirmwarePathFile     = IF->ReadString(SectionName, "LastFirmwarePathFile", "");
            Lang                     = IF->ReadInteger(SectionName, "LangEU", 0);
            iLanguage                = IF->ReadInteger(SectionName, "Language", 0);
            SignalAutoDelayTime      = IF->ReadInteger(SectionName, "SignalAutoDelayTime", 3);
            bOptionDubug             = IF->ReadInteger(SectionName, "OptionDubug", 0);
            bOptionBoardRate232      = IF->ReadInteger(SectionName, "OptionBoardRate232", 4);
            bOptionBoardRateTTL      = IF->ReadInteger(SectionName, "OptionBoardRateTTL", 4);
            bOptionBoardRate485      = IF->ReadInteger(SectionName, "OptionBoardRate485", 4);
            bOptionBH1               = IF->ReadInteger(SectionName, "OptionBH1", 4);
            bOptionJ4                = IF->ReadInteger(SectionName, "OptionJ4", 0);
            bRS485                   = IF->ReadBool(SectionName, "RS485", false);
            bLogviewall              = IF->ReadBool(SectionName, "Log_View_All", false);
            bRealTimeIndex           = IF->ReadInteger(SectionName, "RealTimeIndex", 0);
            bPacketOpen              = IF->ReadBool(SectionName, "PacketOpen", false);
            FontFileName[0][0]       = BasePath + IncludeTrailingPathDelimiter(DestFontPath) + ExtractFileName(IF->ReadString(SectionName, "FontFileName11", "DVS-ENG08x16-Gothic.fnt"));
            FontFileName[0][1]       = BasePath + IncludeTrailingPathDelimiter(DestFontPath) + ExtractFileName(IF->ReadString(SectionName, "FontFileName12", "DVS-KOR16x16-Roman.fnt"));
            FontFileName[0][2]       = BasePath + IncludeTrailingPathDelimiter(DestFontPath) + ExtractFileName(IF->ReadString(SectionName, "FontFileName13", "DVS-USER16x16-ASCII-Roman.fnt"));
            FontFileName[1][0]       = BasePath + IncludeTrailingPathDelimiter(DestFontPath) + ExtractFileName(IF->ReadString(SectionName, "FontFileName21", ""));
            FontFileName[1][1]       = BasePath + IncludeTrailingPathDelimiter(DestFontPath) + ExtractFileName(IF->ReadString(SectionName, "FontFileName22", ""));
            FontFileName[1][2]       = BasePath + IncludeTrailingPathDelimiter(DestFontPath) + ExtractFileName(IF->ReadString(SectionName, "FontFileName23", ""));
            FontFileName[2][0]       = BasePath + IncludeTrailingPathDelimiter(DestFontPath) + ExtractFileName(IF->ReadString(SectionName, "FontFileName31", ""));
            FontFileName[2][1]       = BasePath + IncludeTrailingPathDelimiter(DestFontPath) + ExtractFileName(IF->ReadString(SectionName, "FontFileName32", ""));
            FontFileName[2][2]       = BasePath + IncludeTrailingPathDelimiter(DestFontPath) + ExtractFileName(IF->ReadString(SectionName, "FontFileName33", ""));
            FontFileName[3][0]       = BasePath + IncludeTrailingPathDelimiter(DestFontPath) + ExtractFileName(IF->ReadString(SectionName, "FontFileName41", ""));
            FontFileName[3][1]       = BasePath + IncludeTrailingPathDelimiter(DestFontPath) + ExtractFileName(IF->ReadString(SectionName, "FontFileName42", ""));
            FontFileName[3][2]       = BasePath + IncludeTrailingPathDelimiter(DestFontPath) + ExtractFileName(IF->ReadString(SectionName, "FontFileName43", ""));
            FontFileNameVersion[0][0] = IF->ReadString(SectionName, "FontFileNameVersion11", "");
            FontFileNameVersion[0][1] = IF->ReadString(SectionName, "FontFileNameVersion12", "");
            FontFileNameVersion[0][2] = IF->ReadString(SectionName, "FontFileNameVersion13", "");
            FontFileNameVersion[1][0] = IF->ReadString(SectionName, "FontFileNameVersion21", "");
            FontFileNameVersion[1][1] = IF->ReadString(SectionName, "FontFileNameVersion22", "");
            FontFileNameVersion[1][2] = IF->ReadString(SectionName, "FontFileNameVersion23", "");
            FontFileNameVersion[2][0] = IF->ReadString(SectionName, "FontFileNameVersion31", "");
            FontFileNameVersion[2][1] = IF->ReadString(SectionName, "FontFileNameVersion32", "");
            FontFileNameVersion[2][2] = IF->ReadString(SectionName, "FontFileNameVersion33", "");
            FontFileNameVersion[3][0] = IF->ReadString(SectionName, "FontFileNameVersion41", "");
            FontFileNameVersion[3][1] = IF->ReadString(SectionName, "FontFileNameVersion42", "");
            FontFileNameVersion[3][2] = IF->ReadString(SectionName, "FontFileNameVersion43", "");
            FontCountCheck[0]        = IF->ReadBool(SectionName, "FontCountCheck1", false);
            FontCountCheck[1]        = IF->ReadBool(SectionName, "FontCountCheck2", false);
            FontCountCheck[2]        = IF->ReadBool(SectionName, "FontCountCheck3", false);
            FontCountCheck[3]        = IF->ReadBool(SectionName, "FontCountCheck4", false);
            FontIndex[0][0]          = IF->ReadInteger(SectionName, "FontIndex11", 1);
            FontIndex[0][1]          = IF->ReadInteger(SectionName, "FontIndex12", 5);
            FontIndex[0][2]          = IF->ReadInteger(SectionName, "FontIndex13", 6);
            FontIndex[1][0]          = IF->ReadInteger(SectionName, "FontIndex21", 1);
            FontIndex[1][1]          = IF->ReadInteger(SectionName, "FontIndex22", 5);
            FontIndex[1][2]          = IF->ReadInteger(SectionName, "FontIndex23", 6);
            FontIndex[2][0]          = IF->ReadInteger(SectionName, "FontIndex31", 0);
            FontIndex[2][1]          = IF->ReadInteger(SectionName, "FontIndex32", 0);
            FontIndex[2][2]          = IF->ReadInteger(SectionName, "FontIndex33", 0);
            FontIndex[3][0]          = IF->ReadInteger(SectionName, "FontIndex41", 0);
            FontIndex[3][1]          = IF->ReadInteger(SectionName, "FontIndex42", 0);
            FontIndex[3][2]          = IF->ReadInteger(SectionName, "FontIndex43", 0);
            FontSizeIndex[0]         = IF->ReadInteger(SectionName, "FontSizeIndex1", 2);
            FontSizeIndex[1]         = IF->ReadInteger(SectionName, "FontSizeIndex2", 2);
            FontSizeIndex[2]         = IF->ReadInteger(SectionName, "FontSizeIndex3", 2);
            FontSizeIndex[3]         = IF->ReadInteger(SectionName, "FontSizeIndex4", 2);

            SectionName = "ProtocolInfo";
            ProtocolTabKind          = IF->ReadInteger(SectionName, "TabKind", 0);
            MessageKind              = IF->ReadInteger(SectionName, "MessageKind", 0);
            ProtocolBlockNo          = IF->ReadInteger(SectionName, "BlockNo", 0);
            ProtocolPageNo           = IF->ReadInteger(SectionName, "PageNo", 0);
            ProtocolBlockNo2         = IF->ReadInteger(SectionName, "BlockNo2", 0);
            ProtocolPageNo2          = IF->ReadInteger(SectionName, "PageNo2", 0);
            MessageCount             = IF->ReadInteger(SectionName, "SvrMsgMaxCnt", 10);
            ASCIIBlockNo             = IF->ReadInteger(SectionName, "ASCIIBlockNo", 0);
            ASCIIPageNo              = IF->ReadInteger(SectionName, "ASCIIPageNo", 0);
            if(MessageCount > 50)
                MessageCount = 50;
            AnsiString ProtocolFileName = BasePath + IncludeTrailingPathDelimiter(DestSysPath) + "ProtocolData.dat";
            ZeroMemory(&ProtocolData, sizeof(sProtocolData));
            TFileStream *ProtocolFile = NULL;
            if(FileExists(ProtocolFileName))
            {
                try{
                    ProtocolFile = new TFileStream(ProtocolFileName, fmOpenRead | fmShareCompat);
                    ProtocolFile->Read(&ProtocolData, sizeof(sProtocolData));
                }catch(...){
                }
                if(ProtocolFile)
                {
                    delete ProtocolFile;
                    ProtocolFile = NULL;
                }
            }
            else
            {
                AnsiString asData = "";
                char Buf[3];
                char Buf2[3];
                for(int i = 0; i < 12; i++)
                {
                    for(int j = 0; j < 3; j++)
                    {
                        ProtocolData.ProtocolDispControl[i][j] = 1;
                        //if(i == 11)
                        //    ProtocolData.ProtocolDispType[i][j]  = 0;
                        //else
                            ProtocolData.ProtocolDispType[i][j]  = 1;
                        ProtocolData.ProtocolCodeKind[i][j]  = 0;
                        ProtocolData.ProtocolFontSize[i][j]  = 0;
                        ProtocolData.ProtocolFontKind[i][j]  = 0;
                        asData = EffectType[etNormal];
                        memcpy(&ProtocolData.ProtocolEntEffect[i][j][0], asData.c_str(), asData.Length());
                        memcpy(&ProtocolData.ProtocolLasEffect[i][j][0], asData.c_str(), asData.Length());
                        asData = DirType[edNone];
                        memcpy(&ProtocolData.ProtocolEntDirect[i][j][0], asData.c_str(), asData.Length());
                        memcpy(&ProtocolData.ProtocolLasDirect[i][j][0], asData.c_str(), asData.Length());
                        asData = "20";
                        memcpy(&ProtocolData.ProtocolSpeed[i][j][0], asData.c_str(), asData.Length());
                        asData = "2";
                        memcpy(&ProtocolData.ProtocolDelay[i][j][0], asData.c_str(), asData.Length());
                        ProtocolData.ProtocolStartXPos[i][j] = 0;
                        ProtocolData.ProtocolStartYPos[i][j] = 0;
                        ProtocolData.ProtocolEndXPos[i][j]   = 0;
                        ProtocolData.ProtocolEndYPos[i][j]   = 0;
                        ProtocolData.ProtocolBGImage[i][j]   = 0;
                        ProtocolData.ProtocolColorType[i][j] = 0;
                        if(i == 11)
                        {
                            ProtocolData.ProtocolColor[i][j][0] = 3;
                            ProtocolData.ProtocolBGColor[i][j][0] = 0;
                        }
                        else
                        {
                            asData = "1";
                            memcpy(&ProtocolData.ProtocolColor[i][j][0], asData.c_str(), asData.Length());
                            asData = "0";
                            memcpy(&ProtocolData.ProtocolBGColor[i][j][0], asData.c_str(), asData.Length());
                        }
                        if(i == 0)
                        {
                            if(j == 0)
                                asData = "Hello world~";
                            else
                                asData = "Section " + IntToStr(j);
                        }
                        else
                            asData = "Page Message" + IntToStr(i - 1) + "-" + IntToStr(j);
                        memcpy(&ProtocolData.ProtocolData[i][j][0], asData.c_str(), asData.Length());
                    }
                }
            }
        }
        __finally
        {
            if(IF)
            {
                delete IF;
                IF = NULL;
            }
        }
}
//ini파일 읽어오기 없으면 생성 및 기본값 세팅
//---------------------------------------------------------------------------

void __fastcall TFormMain::SaveIniFile()
{
        // ini 파일 저장 함수
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
            SectionName = "Main";
            IF->WriteString(SectionName,  "PortUse1", PortUse1);
            IF->WriteInteger(SectionName, "BaudRate1", BaudRateUse1);
            IF->WriteString(SectionName, "IPAddress", IPAddress);
            IF->WriteInteger(SectionName, "IPPort", IPPort);
            IF->WriteString(SectionName, "UDPIPAddress", UDPIPAddress);
            IF->WriteString(SectionName, "ServerPort", ServerPort);
            IF->WriteInteger(SectionName, "Main_DSP_PORT", DSPPort);
            //IF->WriteInteger(SectionName, "Main_SRC_PORT", SRCPort);
            IF->WriteInteger(SectionName, "ComKind", ComKind);
            IF->WriteInteger(SectionName, "ModuleWidth", ModuleWidth);
            IF->WriteInteger(SectionName, "ModuleHeight", ModuleHeight);
            IF->WriteInteger(SectionName, "LanDelayTime", LanDelayTime);
            IF->WriteBool(SectionName, "FlagReplyPacket", DelayRun);
            IF->WriteInteger(SectionName, "ReplyPacket0_DelayTime", WaitTime);
            IF->WriteInteger(SectionName, "ReplyPacket1_DelayTime", DelayTime);
            IF->WriteInteger(SectionName, "SvrCommOpenDly", ServerCommOpenDelay);
            IF->WriteInteger(SectionName, "ProgramType", ProgramType);
            IF->WriteInteger(SectionName, "DibdAddress", Controler1);
            IF->WriteBool(SectionName, "FlagCrcPacket", FlagCrcPacket);
            IF->WriteBool(SectionName, "FlagDlePacket", FlagDlePacket);
            IF->WriteInteger(SectionName, "LogFileCnt", LogFileCnt);
            IF->WriteString(SectionName, "OldLogDate", OldLogDate);
            IF->WriteInteger(SectionName, "DisplayType", DisplayType);
            IF->WriteInteger(SectionName, "ColorOder", ColorOder);
            IF->WriteInteger(SectionName, "ScanOder", ScanOder);
            IF->WriteInteger(SectionName, "Bright", Bright);
            IF->WriteInteger(SectionName, "Direction", Direct);
            IF->WriteInteger(SectionName, "DirectCheck", DirectCheck);
            IF->WriteInteger(SectionName, "PorotocolType", PorotocolType);
            IF->WriteString(SectionName, "LastFirmwarePathFile", LastFirmwarePathFile);
            IF->WriteInteger(SectionName, "LangEU", Lang);
            IF->WriteInteger(SectionName, "Language", iLanguage);
            IF->WriteInteger(SectionName, "SignalAutoDelayTime", SignalAutoDelayTime);
            IF->WriteInteger(SectionName, "OptionDubug", bOptionDubug);
            IF->WriteInteger(SectionName, "OptionBoardRate232", bOptionBoardRate232);
            IF->WriteInteger(SectionName, "OptionBoardRateTTL", bOptionBoardRateTTL);
            IF->WriteInteger(SectionName, "OptionBoardRate485", bOptionBoardRate485);
            IF->WriteInteger(SectionName, "OptionBH1", bOptionBH1);
            IF->WriteInteger(SectionName, "OptionJ4", bOptionJ4);
            IF->WriteBool(SectionName, "RS485", bRS485);
            IF->WriteBool(SectionName, "Log_View_All", bLogviewall);
            IF->WriteInteger(SectionName, "RealTimeIndex", bRealTimeIndex);
            IF->WriteBool(SectionName, "PacketOpen", bPacketOpen);
            IF->WriteString(SectionName, "FontFileName11", FontFileName[0][0]);
            IF->WriteString(SectionName, "FontFileName12", FontFileName[0][1]);
            IF->WriteString(SectionName, "FontFileName13", FontFileName[0][2]);
            IF->WriteString(SectionName, "FontFileName21", FontFileName[1][0]);
            IF->WriteString(SectionName, "FontFileName22", FontFileName[1][1]);
            IF->WriteString(SectionName, "FontFileName23", FontFileName[1][2]);
            IF->WriteString(SectionName, "FontFileName31", FontFileName[2][0]);
            IF->WriteString(SectionName, "FontFileName32", FontFileName[2][1]);
            IF->WriteString(SectionName, "FontFileName33", FontFileName[2][2]);
            IF->WriteString(SectionName, "FontFileName41", FontFileName[3][0]);
            IF->WriteString(SectionName, "FontFileName42", FontFileName[3][1]);
            IF->WriteString(SectionName, "FontFileName43", FontFileName[3][2]);
            IF->WriteString(SectionName, "FontFileNameVersion11", FontFileNameVersion[0][0]);
            IF->WriteString(SectionName, "FontFileNameVersion12", FontFileNameVersion[0][1]);
            IF->WriteString(SectionName, "FontFileNameVersion13", FontFileNameVersion[0][2]);
            IF->WriteString(SectionName, "FontFileNameVersion21", FontFileNameVersion[1][0]);
            IF->WriteString(SectionName, "FontFileNameVersion22", FontFileNameVersion[1][1]);
            IF->WriteString(SectionName, "FontFileNameVersion23", FontFileNameVersion[1][2]);
            IF->WriteString(SectionName, "FontFileNameVersion31", FontFileNameVersion[2][0]);
            IF->WriteString(SectionName, "FontFileNameVersion32", FontFileNameVersion[2][1]);
            IF->WriteString(SectionName, "FontFileNameVersion33", FontFileNameVersion[2][2]);
            IF->WriteString(SectionName, "FontFileNameVersion41", FontFileNameVersion[3][0]);
            IF->WriteString(SectionName, "FontFileNameVersion42", FontFileNameVersion[3][1]);
            IF->WriteString(SectionName, "FontFileNameVersion43", FontFileNameVersion[3][2]);
            IF->WriteBool(SectionName, "FontCountCheck1",  FontCountCheck[0]);
            IF->WriteBool(SectionName, "FontCountCheck2",  FontCountCheck[1]);
            IF->WriteBool(SectionName, "FontCountCheck3",  FontCountCheck[2]);
            IF->WriteBool(SectionName, "FontCountCheck4",  FontCountCheck[3]);
            IF->WriteInteger(SectionName, "FontIndex11",   FontIndex[0][0]);
            IF->WriteInteger(SectionName, "FontIndex12",   FontIndex[0][1]);
            IF->WriteInteger(SectionName, "FontIndex13",   FontIndex[0][2]);
            IF->WriteInteger(SectionName, "FontIndex21",   FontIndex[1][0]);
            IF->WriteInteger(SectionName, "FontIndex22",   FontIndex[1][1]);
            IF->WriteInteger(SectionName, "FontIndex23",   FontIndex[1][2]);
            IF->WriteInteger(SectionName, "FontIndex31",   FontIndex[2][0]);
            IF->WriteInteger(SectionName, "FontIndex32",   FontIndex[2][1]);
            IF->WriteInteger(SectionName, "FontIndex33",   FontIndex[2][2]);
            IF->WriteInteger(SectionName, "FontIndex41",   FontIndex[3][0]);
            IF->WriteInteger(SectionName, "FontIndex42",   FontIndex[3][1]);
            IF->WriteInteger(SectionName, "FontIndex43",   FontIndex[3][2]);
            IF->WriteInteger(SectionName, "FontSizeIndex1", FontSizeIndex[0]);
            IF->WriteInteger(SectionName, "FontSizeIndex2", FontSizeIndex[1]);
            IF->WriteInteger(SectionName, "FontSizeIndex3", FontSizeIndex[2]);
            IF->WriteInteger(SectionName, "FontSizeIndex4", FontSizeIndex[3]);

            SectionName = "ProtocolInfo";
            AnsiString ProtocolFileName = BasePath + IncludeTrailingPathDelimiter(DestSysPath) + "ProtocolData.dat";
            TFileStream *ProtocolFile = NULL;
            AnsiString tempData = "";
            try{
                int iCurBlockNo = 0;
                int iCurPageNo = 0;
                /*for(int i = 0; i < 11; i++)
                {
                    iCurPageNo = i;
                    if(rbNo1->Checked)
                        ProtocolBlockNo = 0;
                    else if(rbNo2->Checked)
                        ProtocolBlockNo = 1;
                    else if(rbNo3->Checked)
                        ProtocolBlockNo = 2;
                    iCurBlockNo = ProtocolBlockNo;
                    ProtocolData.ProtocolDispControl[iCurPageNo][iCurBlockNo] = cbDispControl->ItemIndex;
                    ProtocolData.ProtocolDispType[iCurPageNo][iCurBlockNo] = cbDispType->ItemIndex;
                    ProtocolData.ProtocolCodeKind[iCurPageNo][iCurBlockNo] = cbCodeKind->ItemIndex;
                    ProtocolData.ProtocolFontSize[iCurPageNo][iCurBlockNo] = cbFontSize->ItemIndex;
                    ProtocolData.ProtocolFontKind[iCurPageNo][iCurBlockNo] = cbFontKind->ItemIndex;
                    tempData = cbEntEffect->Text;
                    memcpy(&ProtocolData.ProtocolEntEffect[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                    tempData = cbEntDirect->Text;
                    memcpy(&ProtocolData.ProtocolEntDirect[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                    tempData = cbLasEffect->Text;
                    memcpy(&ProtocolData.ProtocolLasEffect[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                    tempData = cbLasDirect->Text;
                    memcpy(&ProtocolData.ProtocolLasDirect[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                    ProtocolData.ProtocolColorType[iCurPageNo][iCurBlockNo] = cbAssistEffect->ItemIndex;
                    tempData = cbSpeed->Text;
                    memcpy(&ProtocolData.ProtocolSpeed[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                    tempData = cbDelay->Text;
                    memcpy(&ProtocolData.ProtocolDelay[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                    ProtocolData.ProtocolStartXPos[iCurPageNo][iCurBlockNo] = cbStartXPos->ItemIndex;
                    ProtocolData.ProtocolStartYPos[iCurPageNo][iCurBlockNo] = cbStartYPos->ItemIndex;
                    ProtocolData.ProtocolEndXPos[iCurPageNo][iCurBlockNo] = cbEndXPos->ItemIndex;
                    ProtocolData.ProtocolEndYPos[iCurPageNo][iCurBlockNo] = cbEndYPos->ItemIndex;
                    ProtocolData.ProtocolBGImage[iCurPageNo][iCurBlockNo] = cbBGImage->ItemIndex;
                    tempData = eColor->Text;
                    memcpy(&ProtocolData.ProtocolColor[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                    tempData = eBGColor->Text;
                    memcpy(&ProtocolData.ProtocolBGColor[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                    tempData = eData->Text;
                    memcpy(&ProtocolData.ProtocolData[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                    tempData = BasePath + IncludeTrailingPathDelimiter(DestSysPath)
                        + "EnvData" + IntToStr(iCurPageNo) + IntToStr(iCurBlockNo) + ".dat";
                    eData->Lines->SaveToFile(tempData);
                }*/
                ProtocolFile = new TFileStream(ProtocolFileName, fmCreate | fmShareCompat);
                ProtocolFile->Write(&ProtocolData, sizeof(sProtocolData));
            }catch(...){
            }
            if(ProtocolFile)
            {
                delete ProtocolFile;
                ProtocolFile = NULL;
            }
            //ProtocolTabKind = pcMessage->ActivePageIndex;
            IF->WriteInteger(SectionName, "TabKind", ProtocolTabKind);
            IF->WriteInteger(SectionName, "MessageKind", MessageKind);
            IF->WriteInteger(SectionName, "BlockNo", ProtocolBlockNo);
            IF->WriteInteger(SectionName, "PageNo", ProtocolPageNo);
            IF->WriteInteger(SectionName, "BlockNo2", ProtocolBlockNo2);
            IF->WriteInteger(SectionName, "PageNo2", ProtocolPageNo2);
            IF->WriteInteger(SectionName, "ASCIIBlockNo", ASCIIBlockNo);
            IF->WriteInteger(SectionName, "ASCIIPageNo", ASCIIPageNo);
            IF->WriteInteger(SectionName, "SvrMsgMaxCnt", MessageCount);
        }
        __finally
        {
            if(IF)
            {
                delete IF;
                IF = NULL;
            }
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::PreviewRoadIniFile()
{
        AnsiString IniFileName = ChangeFileExt(BasePath + "DABITPreview.exe", ".ini");
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
            SectionName = "Main";
            PreviewMode = IF->ReadInteger(SectionName, "PreviewMode", 1);
        }
        __finally
        {
            if(IF)
            {
                delete IF;
                IF = NULL;
            }
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::PreviewSaveIniFile()
{
        AnsiString IniFileName = ChangeFileExt(BasePath + "DABITPreview.exe", ".ini");
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
            SectionName = "Main";
            IF->WriteInteger(SectionName, "PreviewMode", PreviewMode);
        }
        __finally
        {
            if(IF)
            {
                delete IF;
                IF = NULL;
            }
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::RoadLangFile(AnsiString aslang)
{
        // 언어파일 불러오는 함수
        AnsiString IniFileName = "";

        IniFileName = BasePath + DestSysPath + "\\dv_" + aslang + ".ini";
        AnsiString SectionName;

        TIniFile * IF = NULL;
        try
        {
            IF = new TIniFile(IniFileName);
            if(aslang.LowerCase() == "korean")
            {
                SectionName = "MainButton";
                LangFile.BtnPort                        = IF->ReadString(SectionName, "BtnPort",                        "통신 설정");
                LangFile.BtnSize                        = IF->ReadString(SectionName, "BtnSize",                        "전광판 크기");
                LangFile.BtnApply                       = IF->ReadString(SectionName, "BtnApply",                       "적용");
                LangFile.BtnSend                        = IF->ReadString(SectionName, "BtnSend",                        "전송");
                LangFile.BtnTimeRead                    = IF->ReadString(SectionName, "BtnTimeRead",                    "컨트롤러 시간 읽기");
                LangFile.BtnSignalSend                  = IF->ReadString(SectionName, "BtnSignalSend",                  "릴레이 신호 출력");
                LangFile.BtnSyncLED                     = IF->ReadString(SectionName, "BtnSyncLED",                     "PC 시간으로 동기화");
                LangFile.BtnPowerLEDOn                  = IF->ReadString(SectionName, "BtnPowerLEDOn",                  "화면 켜기");
                LangFile.BtnPowerLEDOff                 = IF->ReadString(SectionName, "BtnPowerLEDOff",                 "화면 끄기");
                LangFile.BtnClose                       = IF->ReadString(SectionName, "BtnClose",                       "종료");
                LangFile.BtnLog                         = IF->ReadString(SectionName, "BtnLog",                         "로그 보기");
                LangFile.BtnETC                         = IF->ReadString(SectionName, "BtnETC",                         "설정 및 제어");
                LangFile.BtnSelectAll                   = IF->ReadString(SectionName, "BtnSelectAll",                   "전체선택");
                LangFile.BtnFontSend                    = IF->ReadString(SectionName, "BtnFontSend",                    "폰트 전송");
                LangFile.BtnLEDSetting                  = IF->ReadString(SectionName, "BtnLEDSetting",                  "표출신호 설정");
                LangFile.BtnDIBDDownload                = IF->ReadString(SectionName, "BtnDIBDDownload",                "펌웨어 업그레이드");
                LangFile.BtnAdd                         = IF->ReadString(SectionName, "BtnAdd",                         "추가");
                LangFile.BtnDelete                      = IF->ReadString(SectionName, "BtnDelete",                      "삭제");
                LangFile.BtnSave                        = IF->ReadString(SectionName, "BtnSave",                        "저장");
                LangFile.BtnModify                      = IF->ReadString(SectionName, "BtnModify",                      "수정");
                LangFile.BtnErase                       = IF->ReadString(SectionName, "BtnErase",                       "메시지 초기화");
                LangFile.BtnReset                       = IF->ReadString(SectionName, "BtnReset",                       "컨트롤러 리셋");
                LangFile.BtnFReset                      = IF->ReadString(SectionName, "BtnFReset",                      "공장 초기화");
                LangFile.BtnPSet                        = IF->ReadString(SectionName, "BtnPSet  ",                      "기본 표시 속성 설정");
                LangFile.SpeedButtonPreview             = IF->ReadString(SectionName, "BtnPreview",                     "미리보기");
                LangFile.BtnDABITOptionSetting          = IF->ReadString(SectionName, "BtnDABITOptionSetting",          "보드 기능 설정");
                LangFile.BtnLanguage                    = IF->ReadString(SectionName, "BtnLanguage",                    "프로그램 언어");
                LangFile.BtnProtocol                    = IF->ReadString(SectionName, "BtnProtocol",                    "프로토콜 형식");
                LangFile.BtnScreen                      = IF->ReadString(SectionName, "BtnScreen",                      "화면 채우기");
                LangFile.BtnSetting                     = IF->ReadString(SectionName, "BtnSetting",                     "고급 설정");
                LangFile.BtnPacketRead                  = IF->ReadString(SectionName, "BtnPacketRead",                  "패킷 불러오기");
                SectionName = "FormComport";
                LangFile.FormComportCaption             = IF->ReadString(SectionName, "FormComportCaption",             "통신 설정 창");
                LangFile.FormComportButton1             = IF->ReadString(SectionName, "FormComportButton1",             "포트닫기");
                LangFile.FormComportButton2             = IF->ReadString(SectionName, "FormComportButton2",             "포트열기");
                LangFile.FormComportButton3             = IF->ReadString(SectionName, "FormComportButton3",             "DABIT연결");
                LangFile.FormComportButton4             = IF->ReadString(SectionName, "FormComportButton4",             "접속끊기");
                LangFile.FormComportButton5             = IF->ReadString(SectionName, "FormComportButton5",             "접속하기");
                LangFile.FormComportrbSerial            = IF->ReadString(SectionName, "FormComportrbSerial",            "Serial");
                LangFile.FormComportrbLAN               = IF->ReadString(SectionName, "FormComportrbLAN",               "Client TCP/IP");
                LangFile.FormComportrbServer            = IF->ReadString(SectionName, "FormComportrbServer",            "Server TCP/IP");
                LangFile.FormComportrbUDP               = IF->ReadString(SectionName, "FormComportrbUDP",               "UDP");
                LangFile.FormComportLabel1              = IF->ReadString(SectionName, "FormComportLabel1",              "통신 포트 설정   :");
                LangFile.FormComportLabel2              = IF->ReadString(SectionName, "FormComportLabel2",              "통신 속도 설정   :");
                LangFile.FormComportLabel3              = IF->ReadString(SectionName, "FormComportLabel3",              "DTR 흐름 제어   :");
                LangFile.FormComportLabel4              = IF->ReadString(SectionName, "FormComportLabel4",              "RTS 흐름 제어   :");
                LangFile.FormComportLabel5              = IF->ReadString(SectionName, "FormComportLabel5",              "IP Address :");
                LangFile.FormComportLabel6              = IF->ReadString(SectionName, "FormComportLabel6",              "Port             :");
                LangFile.FormComportcbDDNS              = IF->ReadString(SectionName, "FormComportcbDDNS",              "Dynamic DNS");
                LangFile.FormComportAddress             = IF->ReadString(SectionName, "FormComportAddress",             "RS-485 Address :");
                LangFile.FormComportSystem              = IF->ReadString(SectionName, "FormComportSystem",              "장치관리자");
                LangFile.FormComportSearch              = IF->ReadString(SectionName, "FormComportSearch",              "통신속도찾기");
                LangFile.FormComportRTime               = IF->ReadString(SectionName, "FormComportRTime",               "응답시간설정");
                LangFile.FormComportConnect             = IF->ReadString(SectionName, "FormComportConnect",             "컨트롤러 연결");
                SectionName = "FormProtocol";
                LangFile.FormProtocolCaption = IF->ReadString(SectionName, "FormProtocolCaption",                       "LEDSoft Protocol Simulator(V1.0)");
                LangFile.FormProtocolLabel1 = IF->ReadString(SectionName,  "FormProtocolLabel1",                        "1. 메시지종류");
                LangFile.FormProtocolLabel2 = IF->ReadString(SectionName,  "FormProtocolLabel2",                        "2. 섹션번호");
                LangFile.FormProtocolLabel3 = IF->ReadString(SectionName,  "FormProtocolLabel3",                        "3. 표시제어");
                LangFile.FormProtocolLabel4 = IF->ReadString(SectionName,  "FormProtocolLabel4",                        "4. 표시방법");
                LangFile.FormProtocolLabel5 = IF->ReadString(SectionName,  "FormProtocolLabel5",                        "5. 문자코드");
                LangFile.FormProtocolLabel6 = IF->ReadString(SectionName,  "FormProtocolLabel6",                        "6. 폰트크기");
                LangFile.FormProtocolLabel7 = IF->ReadString(SectionName,  "FormProtocolLabel7",                        "7. 입장효과");
                LangFile.FormProtocolLabel8 = IF->ReadString(SectionName,  "FormProtocolLabel8",                        "8. 퇴장효과");
                LangFile.FormProtocolLabel9 = IF->ReadString(SectionName,  "FormProtocolLabel9",                        "9. 예비");
                LangFile.FormProtocolLabel10 = IF->ReadString(SectionName, "FormProtocolLabel10",                       "10.효과속도");
                LangFile.FormProtocolLabel11 = IF->ReadString(SectionName, "FormProtocolLabel11",                       "11.유지시간(x500ms)");
                LangFile.FormProtocolLabel12 = IF->ReadString(SectionName, "FormProtocolLabel12",                       "12.X축시작");
                LangFile.FormProtocolLabel13 = IF->ReadString(SectionName, "FormProtocolLabel13",                       "13.Y축시작");
                LangFile.FormProtocolLabel14 = IF->ReadString(SectionName, "FormProtocolLabel14",                       "14.X축종료");
                LangFile.FormProtocolLabel15 = IF->ReadString(SectionName, "FormProtocolLabel15",                       "15.Y축종료");
                LangFile.FormProtocolLabel16 = IF->ReadString(SectionName, "FormProtocolLabel16",                       "16.배경이미지 선택");
                LangFile.FormProtocolLabel17 = IF->ReadString(SectionName, "FormProtocolLabel17",                       "17.1.문자색상(0:검정 1:적색 2:녹색 3:노랑 4:파랑 5:분홍 6:하늘색 7:흰색)");
                LangFile.FormProtocolLabel18 = IF->ReadString(SectionName, "FormProtocolLabel18",                       "17.2.문자배경색상(0:검정 1:적색 2:녹색 3:노랑 4:파랑 5:분홍 6:하늘색 7:흰색)");
                LangFile.FormProtocolLabel19 = IF->ReadString(SectionName, "FormProtocolLabel19",                       "18.문자");
                LangFile.FormProtocolLabel20 = IF->ReadString(SectionName, "FormProtocolLabel20",                       "페이지 메시지 설정");
                LangFile.FormProtocolLabel21 = IF->ReadString(SectionName, "FormProtocolLabel21",                       "총개수 등록");
                LangFile.FormProtocolLabel22 = IF->ReadString(SectionName, "FormProtocolLabel22",                       "배경이미지 선택");
                LangFile.FormProtocolLabel23 = IF->ReadString(SectionName, "FormProtocolLabel23",                       "파라메타에 따라 미리보기 화면의 이미지가 다를수 있으므로,");
                LangFile.FormProtocolLabel24 = IF->ReadString(SectionName, "FormProtocolLabel24",                       "실제 전광판 화면을 확인하시기 바랍니다.");
                LangFile.FormProtocolLabel25 = IF->ReadString(SectionName, "FormProtocolLabel25",                       "반복횟수수신");
                LangFile.FormProtocolLabel26 = IF->ReadString(SectionName, "FormProtocolLabel26",                       "전광판 크기");
                LangFile.FormProtocolLabel27 = IF->ReadString(SectionName, "FormProtocolLabel27",                       "메인화면");
                LangFile.FormProtocolLabel28 = IF->ReadString(SectionName, "FormProtocolLabel28",                       "실시간 문구");
                LangFile.FormProtocolLabel29 = IF->ReadString(SectionName, "FormProtocolLabel29",                       "페이지 문구");
                LangFile.FormProtocolLabel30 = IF->ReadString(SectionName, "FormProtocolLabel30",                       "세로 크기");
                LangFile.FormProtocolLabel31 = IF->ReadString(SectionName, "FormProtocolLabel31",                       "가로 크기");
                LangFile.FormProtocolLabel32 = IF->ReadString(SectionName, "FormProtocolLabel32",                       "픽셀당 비트수");
                LangFile.FormProtocolLabel33 = IF->ReadString(SectionName, "FormProtocolLabel33",                       "화면 밝기 설정");
                LangFile.FormProtocolLabel34 = IF->ReadString(SectionName, "FormProtocolLabel34",                       "모듈배열방법");
                LangFile.FormProtocolLabel35 = IF->ReadString(SectionName, "FormProtocolLabel35",                       "프로그램 설정");
                LangFile.FormProtocolLabel36 = IF->ReadString(SectionName, "FormProtocolLabel36",                       "표출안함");
                LangFile.FormProtocolLabel37 = IF->ReadString(SectionName, "FormProtocolLabel37",                       "효과 동시표출");
                LangFile.FormProtocolLabel38 = IF->ReadString(SectionName, "FormProtocolLabel38",                       "효과 개별표출");
                LangFile.FormProtocolTab1    = IF->ReadString(SectionName, "FormProtocolTab1",                          "헥사 프로토콜");
                LangFile.FormProtocolTab2    = IF->ReadString(SectionName, "FormProtocolTab2",                          "아스키 프로토콜");
                LangFile.FormProtocolTab3    = IF->ReadString(SectionName, "FormProtocolTab3",                          "펌웨어 정보");
                LangFile.FormProtocolEffect1  = IF->ReadString(SectionName, "FormProtocolEffect1",                      "사용안함");
                LangFile.FormProtocolEffect2  = IF->ReadString(SectionName, "FormProtocolEffect2",                      "사용함");
                LangFile.FormProtocolEffect3  = IF->ReadString(SectionName, "FormProtocolEffect3",                      "기존목록에표시");
                LangFile.FormProtocolEffect4  = IF->ReadString(SectionName, "FormProtocolEffect4",                      "목록생성후표시");
                LangFile.FormProtocolEffect5  = IF->ReadString(SectionName, "FormProtocolEffect5",                      "KS완성형 한글코드");
                LangFile.FormProtocolEffect6  = IF->ReadString(SectionName, "FormProtocolEffect6",                      "UTF16유니코드");
                LangFile.FormProtocolEffect7  = IF->ReadString(SectionName, "FormProtocolEffect7",                      "행별전송");
                LangFile.FormProtocolEffect8  = IF->ReadString(SectionName, "FormProtocolEffect8",                      "세로방향");
                LangFile.FormProtocolEffect9  = IF->ReadString(SectionName, "FormProtocolEffect9",                      "전체");
                LangFile.FormProtocolEffect10 = IF->ReadString(SectionName, "FormProtocolEffect10",                     "세로|가로방향");
                LangFile.FormProtocolEffect11 = IF->ReadString(SectionName, "FormProtocolEffect11",                     "효과방향");
                LangFile.FormProtocolDateItem1 = IF->ReadString(SectionName, "FormProtocolDateItem1",                   "월");
                LangFile.FormProtocolDateItem2 = IF->ReadString(SectionName, "FormProtocolDateItem2",                   "화");
                LangFile.FormProtocolDateItem3 = IF->ReadString(SectionName, "FormProtocolDateItem3",                   "수");
                LangFile.FormProtocolDateItem4 = IF->ReadString(SectionName, "FormProtocolDateItem4",                   "목");
                LangFile.FormProtocolDateItem5 = IF->ReadString(SectionName, "FormProtocolDateItem5",                   "금");
                LangFile.FormProtocolDateItem6 = IF->ReadString(SectionName, "FormProtocolDateItem6",                   "토");
                LangFile.FormProtocolDateItem7 = IF->ReadString(SectionName, "FormProtocolDateItem7",                   "일");
                LangFile.FormProtocolDirectItem1 = IF->ReadString(SectionName, "FormSignSetingItem1",                   "가로형(default)");
                LangFile.FormProtocolDirectItem2 = IF->ReadString(SectionName, "FormSignSetingItem2",                   "1줄 세로형");
                LangFile.FormProtocolDirectItem3 = IF->ReadString(SectionName, "FormSignSetingItem3",                   "2줄 세로형");
                LangFile.FormProtocolDirectItem4 = IF->ReadString(SectionName, "FormSignSetingItem4",                   "가로형 양면");
                LangFile.FormProtocolDirectItem5 = IF->ReadString(SectionName, "FormSignSetingItem5",                   "1줄 세로형 양면");
                LangFile.FormProtocolDirectItem6 = IF->ReadString(SectionName, "FormSignSetingItem6",                   "2줄 가로형");
                LangFile.FormProtocolColor1 = IF->ReadString(SectionName,  "FormProtocolColor1",                        "흑    색");
                LangFile.FormProtocolColor2 = IF->ReadString(SectionName,  "FormProtocolColor2",                        "적    색");
                LangFile.FormProtocolColor3 = IF->ReadString(SectionName,  "FormProtocolColor3",                        "녹    색");
                LangFile.FormProtocolColor4 = IF->ReadString(SectionName,  "FormProtocolColor4",                        "황    색");
                LangFile.FormProtocolColor5 = IF->ReadString(SectionName,  "FormProtocolColor5",                        "청    색");
                LangFile.FormProtocolColor6 = IF->ReadString(SectionName,  "FormProtocolColor6",                        "자 홍 색");
                LangFile.FormProtocolColor7 = IF->ReadString(SectionName,  "FormProtocolColor7",                        "청 록 색");
                LangFile.FormProtocolColor8 = IF->ReadString(SectionName,  "FormProtocolColor8",                        "흰    색");
                SectionName = "FormLEDSet";
                LangFile.FormLEDSetCaption        = IF->ReadString(SectionName, "FormLEDSetCaption", "표출신호 설정 창");
                LangFile.FormLEDSetLabel1         = IF->ReadString(SectionName, "FormLEDSetLabel1", "메모 :");
                LangFile.FormLEDSetLabel2         = IF->ReadString(SectionName, "FormLEDSetLabel2", "표출신호");
                LangFile.FormLEDSetLabel3         = IF->ReadString(SectionName, "FormLEDSetLabel3", "잔상 지연 설정");
                SectionName = "FormFont";
                LangFile.FormFontCaption      = IF->ReadString(SectionName, "FormFontCaption",     "폰트 전송 창");
                LangFile.FormFontNameCaption  = IF->ReadString(SectionName, "FormFontNameCaption", "폰트 파일명 전송 창");
                LangFile.FormFontLabel1       = IF->ReadString(SectionName, "FormFontLabel1",      "폰트");
                LangFile.FormFontLabel2       = IF->ReadString(SectionName, "FormFontLabel2",      "경로");
                LangFile.FormFontLabel3       = IF->ReadString(SectionName, "FormFontLabel3",      "경로");
                LangFile.FormFontLabel4       = IF->ReadString(SectionName, "FormFontLabel4",      "경로");
                LangFile.FormFontLabel5       = IF->ReadString(SectionName, "FormFontLabel5",      "경로");
                LangFile.FormFontLabel6       = IF->ReadString(SectionName, "FormFontLabel6",      "폰트그룹");
                LangFile.FormFontLabel7       = IF->ReadString(SectionName, "FormFontLabel7",      "폰트파일명");
                LangFile.FormFontItem[0]      = IF->ReadString(SectionName, "FormFontItem1",       "영어(ASCII)");
                LangFile.FormFontItem[1]      = IF->ReadString(SectionName, "FormFontItem2",       "유니코드 완성형");
                LangFile.FormFontItem[2]      = IF->ReadString(SectionName, "FormFontItem3",       "유니코드 일본어");
                LangFile.FormFontItem[3]      = IF->ReadString(SectionName, "FormFontItem4",       "유니코드 중국어");
                LangFile.FormFontItem[4]      = IF->ReadString(SectionName, "FormFontItem5",       "한글조합형");
                LangFile.FormFontItem[5]      = IF->ReadString(SectionName, "FormFontItem6",       "사용자폰트");
                LangFile.FormFontItem[6]      = IF->ReadString(SectionName, "FormFontItem7",       "유니코드전체");
                SectionName = "FormTransfer";
                LangFile.FormTransferCaption1 = IF->ReadString(SectionName, "FormTransferCaption1", "송신 시작");
                LangFile.FormTransferCaption2 = IF->ReadString(SectionName, "FormTransferCaption2", "송신 끝");
                LangFile.FormTransferCaption3 = IF->ReadString(SectionName, "FormTransferCaption3", "송신 중...");
                LangFile.FormTransferLabel1   = IF->ReadString(SectionName, "FormTransferLabel1",   "전체 파일 전송률");
                LangFile.FormTransferLabel2   = IF->ReadString(SectionName, "FormTransferLabel2",   "개별 파일 전송률");
                SectionName = "FormLog";
                LangFile.FormLog = IF->ReadString(SectionName, "FormLog", "로그 보기 창");
                SectionName = "FormETC";
                LangFile.FormETC = IF->ReadString(SectionName, "FormETC", "기능 설정 창");
                SectionName = "FormFirmware";
                LangFile.FormFirmware = IF->ReadString(SectionName, "FormFirmware", "펌웨어 업그레이드 창");
                SectionName = "FormASCIISet";
                LangFile.FormASCIISet = IF->ReadString(SectionName, "FormASCIISet", "메시지 기본 표시 속성 설정 창");
                SectionName = "FormDABITOption";
                LangFile.FormDABITOption = IF->ReadString(SectionName, "FormDABITOption", "보드 기능 설정 창");
                SectionName = "FormBluetooth";
                LangFile.FormBluetoothCaption = IF->ReadString(SectionName, "FormBluetoothCaption", "블루투스 설정창");
                LangFile.FormBluetoothLabel1 = IF->ReadString(SectionName,  "FormBluetoothLabel1",  "블루투스 이름");
                LangFile.FormBluetoothLabel2 = IF->ReadString(SectionName,  "FormBluetoothLabel2",  "블루투스 비밀번호");
                LangFile.FormBluetoothLabel3 = IF->ReadString(SectionName,  "FormBluetoothLabel3",  "Set DelayTime(ms)");
                LangFile.FormBluetoothButton1 = IF->ReadString(SectionName,  "FormBluetoothButton1",  "Set");
                LangFile.FormBluetoothButton2 = IF->ReadString(SectionName,  "FormBluetoothButton2",  "Search");
                LangFile.FormBluetoothButton3 = IF->ReadString(SectionName,  "FormBluetoothButton3",  "Set");
                LangFile.FormBluetoothButton4 = IF->ReadString(SectionName,  "FormBluetoothButton4",  "BeginBLE");
                LangFile.FormBluetoothButton5 = IF->ReadString(SectionName,  "FormBluetoothButton5",  "EndBLE");
                SectionName = "Effect";
                LangFile.NoEffect     = IF->ReadString(SectionName, "NoEffect",     "사용 안함");
                LangFile.EffectType1  = IF->ReadString(SectionName, "EffectType1",  "정지 효과");
                LangFile.EffectType2  = IF->ReadString(SectionName, "EffectType2",  "전체 효과");
                LangFile.EffectType3  = IF->ReadString(SectionName, "EffectType3",  "이동 하기");
                LangFile.EffectType4  = IF->ReadString(SectionName, "EffectType4",  "닦아 내기");
                LangFile.EffectType5  = IF->ReadString(SectionName, "EffectType5",  "블라 인드");
                LangFile.EffectType6  = IF->ReadString(SectionName, "EffectType6",  "커튼 효과");
                LangFile.EffectType7  = IF->ReadString(SectionName, "EffectType7",  "축소 효과");
                LangFile.EffectType8  = IF->ReadString(SectionName, "EffectType8",  "확대 효과");
                LangFile.EffectType9  = IF->ReadString(SectionName, "EffectType9",  "회전 효과");
                LangFile.EffectType10 = IF->ReadString(SectionName, "EffectType10", "배경깜박이기");
                LangFile.EffectType11 = IF->ReadString(SectionName, "EffectType11", "색상깜박이기");
                LangFile.EffectType12 = IF->ReadString(SectionName, "EffectType12", "3D 효과");
                LangFile.EffectType13 = IF->ReadString(SectionName, "EffectType13", "테스트모드");
                LangFile.EffectType14 = IF->ReadString(SectionName, "EffectType14", "");
                LangFile.EffectType15 = IF->ReadString(SectionName, "EffectType15", "");
                LangFile.EffectType16 = IF->ReadString(SectionName, "EffectType16", "");
                LangFile.EffectType17 = IF->ReadString(SectionName, "EffectType17", "");
                LangFile.EffectType18 = IF->ReadString(SectionName, "EffectType18", "");
                LangFile.EffectType19 = IF->ReadString(SectionName, "EffectType19", "");
                LangFile.EffectType20 = IF->ReadString(SectionName, "EffectType20", "");
                LangFile.DirType1  = IF->ReadString(SectionName, "DirType1",  "방향 없음");
                LangFile.DirType2  = IF->ReadString(SectionName, "DirType2",  "밝아 지기");
                LangFile.DirType3  = IF->ReadString(SectionName, "DirType3",  "어두워지기");
                LangFile.DirType4  = IF->ReadString(SectionName, "DirType4",  "수평 반사");
                LangFile.DirType5  = IF->ReadString(SectionName, "DirType5",  "수직 반사");
                LangFile.DirType6  = IF->ReadString(SectionName, "DirType6",  "왼     쪽");
                LangFile.DirType7  = IF->ReadString(SectionName, "DirType7",  "오 른 쪽");
                LangFile.DirType8  = IF->ReadString(SectionName, "DirType8",  "위     로");
                LangFile.DirType9  = IF->ReadString(SectionName, "DirType9",  "아 래 로");
                LangFile.DirType10 = IF->ReadString(SectionName, "DirType10", "왼쪽 위로");
                LangFile.DirType11 = IF->ReadString(SectionName, "DirType11", "위로아래로");
                LangFile.DirType12 = IF->ReadString(SectionName, "DirType12", "왼쪽 이어서");
                LangFile.DirType13 = IF->ReadString(SectionName, "DirType13", "왼쪽아래로");
                LangFile.DirType14 = IF->ReadString(SectionName, "DirType14", "오른쪽위로");
                LangFile.DirType15 = IF->ReadString(SectionName, "DirType15", "오른쪽아래로");
                LangFile.DirType16 = IF->ReadString(SectionName, "DirType16", "중앙 으로");
                LangFile.DirType17 = IF->ReadString(SectionName, "DirType17", "수평밖으로");
                LangFile.DirType18 = IF->ReadString(SectionName, "DirType18", "수평안으로");
                LangFile.DirType19 = IF->ReadString(SectionName, "DirType19", "수직밖으로");
                LangFile.DirType20 = IF->ReadString(SectionName, "DirType20", "수직안으로");
                LangFile.DirType21 = IF->ReadString(SectionName, "DirType21", "수평안밖으로");
                LangFile.DirType22 = IF->ReadString(SectionName, "DirType22", "수직안밖으로");
                LangFile.DirType23 = IF->ReadString(SectionName, "DirType23", "시계 방향1");
                LangFile.DirType24 = IF->ReadString(SectionName, "DirType24", "시계 반대1");
                LangFile.DirType25 = IF->ReadString(SectionName, "DirType25", "시계 방향2");
                LangFile.DirType26 = IF->ReadString(SectionName, "DirType26", "시계 반대2");
                LangFile.DirType27 = IF->ReadString(SectionName, "DirType27", "빨    강");
                LangFile.DirType28 = IF->ReadString(SectionName, "DirType28", "녹    색");
                LangFile.DirType29 = IF->ReadString(SectionName, "DirType29", "파    랑");
                LangFile.DirType30 = IF->ReadString(SectionName, "DirType30", "노    랑");
                LangFile.DirType31 = IF->ReadString(SectionName, "DirType31", "전체(순차적)");
                LangFile.DirType32 = IF->ReadString(SectionName, "DirType32", "전체(동시에)");
                LangFile.DirType33 = IF->ReadString(SectionName, "DirType33", "순차 효과");
                LangFile.DirType34 = IF->ReadString(SectionName, "DirType34", "무작위효과");
                LangFile.DirType35 = IF->ReadString(SectionName, "DirType35", "효과없음");
                LangFile.DirType36 = IF->ReadString(SectionName, "DirType36", "효과없음");
                LangFile.DirType37 = IF->ReadString(SectionName, "DirType37", "왼쪽이동");
                LangFile.DirType38 = IF->ReadString(SectionName, "DirType38", "오른쪽이동");
                LangFile.DirType39 = IF->ReadString(SectionName, "DirType39", "위로이동");
                LangFile.DirType40 = IF->ReadString(SectionName, "DirType40", "아래로이동");
                LangFile.DirType41 = IF->ReadString(SectionName, "DirType41", "삭제후효과없음");
                LangFile.DirType42 = IF->ReadString(SectionName, "DirType42", "삭제후왼쪽");
                LangFile.DirType43 = IF->ReadString(SectionName, "DirType43", "삭제후오른쪽");
                LangFile.DirType44 = IF->ReadString(SectionName, "DirType44", "삭제후위로");
                LangFile.DirType45 = IF->ReadString(SectionName, "DirType45", "삭제후아래로");
                LangFile.DirType46 = IF->ReadString(SectionName, "DirType46", "스켄모드");
                LangFile.DirType47 = IF->ReadString(SectionName, "DirType47", "");
                LangFile.DirType48 = IF->ReadString(SectionName, "DirType48", "");
                LangFile.DirType49 = IF->ReadString(SectionName, "DirType49", "");
                LangFile.DirType50 = IF->ReadString(SectionName, "DirType50", "");
                SectionName = "Message";
                LangFile.MessageCaption = IF->ReadString(SectionName,  "MessageCaption",  "메세지 창");
                LangFile.MessageButton01 = IF->ReadString(SectionName, "MessageButton01", "예");
                LangFile.MessageButton02 = IF->ReadString(SectionName, "MessageButton02", "아니오");
                LangFile.MessageButton03 = IF->ReadString(SectionName, "MessageButton03", "취소");
                LangFile.MessageButton04 = IF->ReadString(SectionName, "MessageButton04", "확인");
                LangFile.MessageButton05 = IF->ReadString(SectionName, "MessageButton05", "저장");
                LangFile.MessageButton06 = IF->ReadString(SectionName, "MessageButton06", "전체전송");
                LangFile.MessageButton07 = IF->ReadString(SectionName, "MessageButton07", "개별전송");
                LangFile.MessageButton08 = IF->ReadString(SectionName, "MessageButton08", "텍스트");
                LangFile.MessageButton09 = IF->ReadString(SectionName, "MessageButton09", "그래픽");
                LangFile.MessageButton10 = IF->ReadString(SectionName, "MessageButton10", "닫기");
                LangFile.MessageButton11 = IF->ReadString(SectionName, "MessageButton11", "DB저장후 닫기");
                LangFile.MessageButton12 = IF->ReadString(SectionName, "MessageButton12", "1번목록 붙여놓기");
                LangFile.MessageButton13 = IF->ReadString(SectionName, "MessageButton13", "그룹 붙여놓기");
                LangFile.MessageButton14 = IF->ReadString(SectionName, "MessageButton14", "자동해제");
                LangFile.MessageButton15 = IF->ReadString(SectionName, "MessageButton15", "읽기");
                LangFile.MessageButton16 = IF->ReadString(SectionName, "MessageButton16", "선택안함");
                LangFile.MessageButton17 = IF->ReadString(SectionName, "MessageButton17", "설정");
                LangFile.MessageButton18 = IF->ReadString(SectionName, "MessageButton18", "초기화");
                LangFile.MessageButton19 = IF->ReadString(SectionName, "MessageButton19", "작성");
                LangFile.MessageButton20 = IF->ReadString(SectionName, "MessageButton20", "정지");
                LangFile.MessageButton21 = IF->ReadString(SectionName, "MessageButton21", "열기");
                LangFile.MessageButton22 = IF->ReadString(SectionName, "MessageButton22", "자동전송");
                LangFile.MessageButton23 = IF->ReadString(SectionName, "MessageButton23", "개");
                LangFile.MessageButton24 = IF->ReadString(SectionName, "MessageButton24", "총개수");
                LangFile.Message01       = IF->ReadString(SectionName, "Message01", "가장빠름");
                LangFile.Message02       = IF->ReadString(SectionName, "Message02", "가장늦음");
                LangFile.Message03       = IF->ReadString(SectionName, "Message03", "Power On");
                LangFile.Message04       = IF->ReadString(SectionName, "Message04", "Power Off");
                LangFile.Message05       = IF->ReadString(SectionName, "Message05", "통신을 할 수 없습니다.");
                LangFile.Message06       = IF->ReadString(SectionName, "Message06", "시간 동기화 설정");
                LangFile.Message07       = IF->ReadString(SectionName, "Message07", "전광판 크기 설정");
                LangFile.Message08       = IF->ReadString(SectionName, "Message08", "일반문구 메모리초기화");
                LangFile.Message09       = IF->ReadString(SectionName, "Message09", "일반문구 등록");
                LangFile.Message10       = IF->ReadString(SectionName, "Message10", "배경이미지 선택");
                LangFile.Message11       = IF->ReadString(SectionName, "Message11", "존재하지 않는 명령입니다.");
                LangFile.Message12       = IF->ReadString(SectionName, "Message12", "DABIT의 펌웨어에서 지원하지 않는 기능입니다.");
                LangFile.Message13       = IF->ReadString(SectionName, "Message13", "전송한 데이터 값이 정상이 아닙니다.");
                LangFile.Message14       = IF->ReadString(SectionName, "Message14", "파일 경로 :");
                LangFile.Message15       = IF->ReadString(SectionName, "Message15", "파일을 읽을 수 없습니다.");
                LangFile.Message16       = IF->ReadString(SectionName, "Message16", "전송할 업로드파일이 제어보드의 펌웨어 파일이 아닙니다.");
                LangFile.Message17       = IF->ReadString(SectionName, "Message17", "일반문구 메모리 초기화");
                LangFile.Message18       = IF->ReadString(SectionName, "Message18", "3,145,728Byte 이상의 폰트를 선택할 수 없습니다.");
                LangFile.Message19       = IF->ReadString(SectionName, "Message19", "폰트크기를 확인하세요!");
                LangFile.Message20       = IF->ReadString(SectionName, "Message20", "Enable은 통신속도에 영향을 줄수있습니다. 계속 진행하시겠습니까?");
            }
            else
            {
                SectionName = "MainButton";
                LangFile.BtnPort                        = IF->ReadString(SectionName, "BtnPort",                        "Communication Method");
                LangFile.BtnSize                        = IF->ReadString(SectionName, "BtnSize",                        "Screen Setting");
                LangFile.BtnApply                       = IF->ReadString(SectionName, "BtnApply",                       "Apply");
                LangFile.BtnSend                        = IF->ReadString(SectionName, "BtnSend",                        "Send");
                LangFile.BtnTimeRead                    = IF->ReadString(SectionName, "BtnTimeRead",                    "Read Time from Controller");
                LangFile.BtnSignalSend                  = IF->ReadString(SectionName, "BtnSignalSend",                  "Send Relay Output Signal");
                LangFile.BtnSyncLED                     = IF->ReadString(SectionName, "BtnSyncLED",                     "Update Time to Controller");
                LangFile.BtnPowerLEDOn                  = IF->ReadString(SectionName, "BtnPowerLEDOn",                  "Turn On Screen");
                LangFile.BtnPowerLEDOff                 = IF->ReadString(SectionName, "BtnPowerLEDOff",                 "Turn Off Screen");
                LangFile.BtnClose                       = IF->ReadString(SectionName, "BtnPClose",                      "Exit");
                LangFile.BtnLog                         = IF->ReadString(SectionName, "BtnLog",                         "Communication Log");
                LangFile.BtnETC                         = IF->ReadString(SectionName, "BtnETC",                         "Special Functions");
                LangFile.BtnSelectAll                   = IF->ReadString(SectionName, "BtnSelectAll",                   "All Check");
                LangFile.BtnFontSend                    = IF->ReadString(SectionName, "BtnFontSend",                    "Font Transfer");
                LangFile.BtnLEDSetting                  = IF->ReadString(SectionName, "BtnLEDSetting",                  "LED Signal Type");
                LangFile.BtnDIBDDownload                = IF->ReadString(SectionName, "BtnDIBDDownload",                "Firmware Upgrade");
                LangFile.BtnAdd                         = IF->ReadString(SectionName, "BtnAdd",                         "Add");
                LangFile.BtnDelete                      = IF->ReadString(SectionName, "BtnDelete",                      "Delete");
                LangFile.BtnSave                        = IF->ReadString(SectionName, "BtnSave",                        "Save");
                LangFile.BtnModify                      = IF->ReadString(SectionName, "BtnModify",                      "Modify");
                LangFile.BtnErase                       = IF->ReadString(SectionName, "BtnErase",                       "Reset Messages");
                LangFile.BtnReset                       = IF->ReadString(SectionName, "BtnReset",                       "CPU Reset");
                LangFile.BtnFReset                      = IF->ReadString(SectionName, "BtnFReset",                      "Factory Reset");
                LangFile.BtnPSet                        = IF->ReadString(SectionName, "BtnPSet  ",                      "Set Message Properties");
                LangFile.SpeedButtonPreview             = IF->ReadString(SectionName, "BtnPreview",                     "Preview");
                LangFile.BtnDABITOptionSetting          = IF->ReadString(SectionName, "BtnDABITOptionSetting",          "Board Functions");
                LangFile.BtnLanguage                    = IF->ReadString(SectionName, "BtnLanguage",                    "Language");
                LangFile.BtnProtocol                    = IF->ReadString(SectionName, "BtnProtocol",                    "Type of Protocol");
                LangFile.BtnScreen                      = IF->ReadString(SectionName, "BtnScreen",                      "Fill Screen Color");
                LangFile.BtnSetting                     = IF->ReadString(SectionName, "BtnSetting",                     "Advanced Settings");
                LangFile.BtnPacketRead                  = IF->ReadString(SectionName, "BtnPacketRead",                  "Open Packets");
                SectionName = "FormComport";
                LangFile.FormComportCaption             = IF->ReadString(SectionName, "FormComportCaption",             "Communication Setting");
                LangFile.FormComportButton1             = IF->ReadString(SectionName, "FormComportButton1",             "Close Port");
                LangFile.FormComportButton2             = IF->ReadString(SectionName, "FormComportButton2",             "Open Port");
                LangFile.FormComportButton3             = IF->ReadString(SectionName, "FormComportButton3",             "Connect DABIT");
                LangFile.FormComportButton4             = IF->ReadString(SectionName, "FormComportButton4",             "Disconnected");
                LangFile.FormComportButton5             = IF->ReadString(SectionName, "FormComportButton5",             "Connected");
                LangFile.FormComportrbSerial            = IF->ReadString(SectionName, "FormComportrbSerial",            "Serial");
                LangFile.FormComportrbLAN               = IF->ReadString(SectionName, "FormComportrbLAN",               "Client TCP/IP");
                LangFile.FormComportrbServer            = IF->ReadString(SectionName, "FormComportrbServer",            "Server TCP/IP");
                LangFile.FormComportrbUDP               = IF->ReadString(SectionName, "FormComportrbUDP",               "UDP");
                LangFile.FormComportLabel1              = IF->ReadString(SectionName, "FormComportLabel1",              "COM Port :");
                LangFile.FormComportLabel2              = IF->ReadString(SectionName, "FormComportLabel2",              "Baudrate   :");
                LangFile.FormComportLabel3              = IF->ReadString(SectionName, "FormComportLabel3",              "DTR Control :");
                LangFile.FormComportLabel4              = IF->ReadString(SectionName, "FormComportLabel4",              "RTS Control :");
                LangFile.FormComportLabel5              = IF->ReadString(SectionName, "FormComportLabel5",              "IP Address :");
                LangFile.FormComportLabel6              = IF->ReadString(SectionName, "FormComportLabel6",              "Port             :");
                LangFile.FormComportcbDDNS              = IF->ReadString(SectionName, "FormComportcbDDNS",              "Dynamic DNS");
                LangFile.FormComportAddress             = IF->ReadString(SectionName, "FormComportAddress",             "RS-485 Address :");
                LangFile.FormComportSystem              = IF->ReadString(SectionName, "FormComportSystem",              "Device Manager");
                LangFile.FormComportSearch              = IF->ReadString(SectionName, "FormComportSearch",              "Baudrate Search");
                LangFile.FormComportRTime               = IF->ReadString(SectionName, "FormComportRTime",               "Response Timeout");
                LangFile.FormComportConnect             = IF->ReadString(SectionName, "FormComportConnect",             "Connect DABIT");
                SectionName = "FormProtocol";
                LangFile.FormProtocolCaption = IF->ReadString(SectionName, "FormProtocolCaption",                       "LEDSoft Protocol Simulator(V1.0)");
                LangFile.FormProtocolLabel1 = IF->ReadString(SectionName,  "FormProtocolLabel1",                        "1. Message Type");
                LangFile.FormProtocolLabel2 = IF->ReadString(SectionName,  "FormProtocolLabel2",                        "2. Section No.");
                LangFile.FormProtocolLabel3 = IF->ReadString(SectionName,  "FormProtocolLabel3",                        "3. Display Control");
                LangFile.FormProtocolLabel4 = IF->ReadString(SectionName,  "FormProtocolLabel4",                        "4. Display Method");
                LangFile.FormProtocolLabel5 = IF->ReadString(SectionName,  "FormProtocolLabel5",                        "5. Text Code");
                LangFile.FormProtocolLabel6 = IF->ReadString(SectionName,  "FormProtocolLabel6",                        "6. Font Size");
                LangFile.FormProtocolLabel7 = IF->ReadString(SectionName,  "FormProtocolLabel7",                        "7. Entry Effect");
                LangFile.FormProtocolLabel8 = IF->ReadString(SectionName,  "FormProtocolLabel8",                        "8. Exit Effect");
                LangFile.FormProtocolLabel9 = IF->ReadString(SectionName,  "FormProtocolLabel9",                        "9. Spare");
                LangFile.FormProtocolLabel10 = IF->ReadString(SectionName, "FormProtocolLabel10",                       "10.Effect Speed");
                LangFile.FormProtocolLabel11 = IF->ReadString(SectionName, "FormProtocolLabel11",                       "11.Stay Time(x500ms)");
                LangFile.FormProtocolLabel12 = IF->ReadString(SectionName, "FormProtocolLabel12",                       "12.X Start Pos.");
                LangFile.FormProtocolLabel13 = IF->ReadString(SectionName, "FormProtocolLabel13",                       "13.Y Start Pos.");
                LangFile.FormProtocolLabel14 = IF->ReadString(SectionName, "FormProtocolLabel14",                       "14.X End Pos.");
                LangFile.FormProtocolLabel15 = IF->ReadString(SectionName, "FormProtocolLabel15",                       "15.Y End Pos.");
                LangFile.FormProtocolLabel16 = IF->ReadString(SectionName, "FormProtocolLabel16",                       "16.B.G. image No.");
                LangFile.FormProtocolLabel17 = IF->ReadString(SectionName, "FormProtocolLabel17",                       "17.1.Text Color(0:None 1:Red 2:Green 3:Yellow 4:Blue 5:Purple 6:SkyBlue 7:White)");
                LangFile.FormProtocolLabel18 = IF->ReadString(SectionName, "FormProtocolLabel18",                       "17.2.Text Background Color(0:None 1:Red 2:Green 3:Yellow 4:Blue 5:Purple 6:SkyBlue 7:White)");
                LangFile.FormProtocolLabel19 = IF->ReadString(SectionName, "FormProtocolLabel19",                       "18.Text Message");
                LangFile.FormProtocolLabel20 = IF->ReadString(SectionName, "FormProtocolLabel20",                       "Set Page Messages");
                LangFile.FormProtocolLabel21 = IF->ReadString(SectionName, "FormProtocolLabel21",                       "Total Pages");
                LangFile.FormProtocolLabel22 = IF->ReadString(SectionName, "FormProtocolLabel22",                       "Select Background Image");
                LangFile.FormProtocolLabel23 = IF->ReadString(SectionName, "FormProtocolLabel23",                       "Note : Please be sure to confirm the actual display image on LED screen");
                LangFile.FormProtocolLabel24 = IF->ReadString(SectionName, "FormProtocolLabel24",                       "from which some preview image could differ, depending on the parameter.");
                LangFile.FormProtocolLabel25 = IF->ReadString(SectionName, "FormProtocolLabel25",                       "Receive repetitions");
                LangFile.FormProtocolLabel26 = IF->ReadString(SectionName, "FormProtocolLabel26",                       "Set Screen Size");
                LangFile.FormProtocolLabel27 = IF->ReadString(SectionName, "FormProtocolLabel27",                       "Dsp.Main");
                LangFile.FormProtocolLabel28 = IF->ReadString(SectionName, "FormProtocolLabel28",                       "RealTime Message");
                LangFile.FormProtocolLabel29 = IF->ReadString(SectionName, "FormProtocolLabel29",                       "Page Message");
                LangFile.FormProtocolLabel30 = IF->ReadString(SectionName, "FormProtocolLabel30",                       "Vertical Size");
                LangFile.FormProtocolLabel31 = IF->ReadString(SectionName, "FormProtocolLabel31",                       "Horizontial Size");
                LangFile.FormProtocolLabel32 = IF->ReadString(SectionName, "FormProtocolLabel32",                       "Bit Per Pixel");
                LangFile.FormProtocolLabel33 = IF->ReadString(SectionName, "FormProtocolLabel33",                       "Set Brightness");
                LangFile.FormProtocolLabel34 = IF->ReadString(SectionName, "FormProtocolLabel34",                       "Module Array");
                LangFile.FormProtocolLabel35 = IF->ReadString(SectionName, "FormProtocolLabel35",                       "Program Setting");
                LangFile.FormProtocolLabel36 = IF->ReadString(SectionName, "FormProtocolLabel36",                       "No display");
                LangFile.FormProtocolLabel37 = IF->ReadString(SectionName, "FormProtocolLabel37",                       "Effect #1");
                LangFile.FormProtocolLabel38 = IF->ReadString(SectionName, "FormProtocolLabel38",                       "Effect #2");
                LangFile.FormProtocolLabel39 = IF->ReadString(SectionName, "FormProtocolLabel39",                       "Set RealTime Message");
                LangFile.FormProtocolTab1    = IF->ReadString(SectionName, "FormProtocolTab1",                          "Hex Message");
                LangFile.FormProtocolTab2    = IF->ReadString(SectionName, "FormProtocolTab2",                          "ASCII Message");
                LangFile.FormProtocolTab3    = IF->ReadString(SectionName, "FormProtocolTab3",                          "Information");
                LangFile.FormProtocolEffect1  = IF->ReadString(SectionName, "FormProtocolEffect1",                      "Disabled");
                LangFile.FormProtocolEffect2  = IF->ReadString(SectionName, "FormProtocolEffect2",                      "Enabled");
                LangFile.FormProtocolEffect3  = IF->ReadString(SectionName, "FormProtocolEffect3",                      "Wipe List");
                LangFile.FormProtocolEffect4  = IF->ReadString(SectionName, "FormProtocolEffect4",                      "Add List");
                LangFile.FormProtocolEffect5  = IF->ReadString(SectionName, "FormProtocolEffect5",                      "KS(Default)");
                LangFile.FormProtocolEffect6  = IF->ReadString(SectionName, "FormProtocolEffect6",                      "UniCode");
                LangFile.FormProtocolEffect7  = IF->ReadString(SectionName, "FormProtocolEffect7",                      "Line");
                LangFile.FormProtocolEffect8  = IF->ReadString(SectionName, "FormProtocolEffect8",                      "Vertical");
                LangFile.FormProtocolEffect9  = IF->ReadString(SectionName, "FormProtocolEffect9",                      "All");
                LangFile.FormProtocolEffect10 = IF->ReadString(SectionName, "FormProtocolEffect10",                     "Verti.|Horiz.");
                LangFile.FormProtocolEffect11 = IF->ReadString(SectionName, "FormProtocolEffect11",                     "Direction");
                LangFile.FormProtocolDateItem1 = IF->ReadString(SectionName, "FormProtocolDateItem1",                   "Mon");
                LangFile.FormProtocolDateItem2 = IF->ReadString(SectionName, "FormProtocolDateItem2",                   "Tue");
                LangFile.FormProtocolDateItem3 = IF->ReadString(SectionName, "FormProtocolDateItem3",                   "Wed");
                LangFile.FormProtocolDateItem4 = IF->ReadString(SectionName, "FormProtocolDateItem4",                   "Thu");
                LangFile.FormProtocolDateItem5 = IF->ReadString(SectionName, "FormProtocolDateItem5",                   "Fri");
                LangFile.FormProtocolDateItem6 = IF->ReadString(SectionName, "FormProtocolDateItem6",                   "Sat");
                LangFile.FormProtocolDateItem7 = IF->ReadString(SectionName, "FormProtocolDateItem7",                   "Sun");
                LangFile.FormProtocolDirectItem1 = IF->ReadString(SectionName, "FormSignSetingItem1",                   "Horizontal(default)");
                LangFile.FormProtocolDirectItem2 = IF->ReadString(SectionName, "FormSignSetingItem2",                   "1Line Vertical");
                LangFile.FormProtocolDirectItem3 = IF->ReadString(SectionName, "FormSignSetingItem3",                   "2Line Vertical");
                LangFile.FormProtocolDirectItem4 = IF->ReadString(SectionName, "FormSignSetingItem4",                   "Horizontal Twin");
                LangFile.FormProtocolDirectItem5 = IF->ReadString(SectionName, "FormSignSetingItem5",                   "1Line Vertical Twin");
                LangFile.FormProtocolDirectItem6 = IF->ReadString(SectionName, "FormSignSetingItem6",                   "2Line Horizontal");
                LangFile.FormProtocolColor1 = IF->ReadString(SectionName,  "FormProtocolColor1",                        "Black");
                LangFile.FormProtocolColor2 = IF->ReadString(SectionName,  "FormProtocolColor2",                        "Red");
                LangFile.FormProtocolColor3 = IF->ReadString(SectionName,  "FormProtocolColor3",                        "Green");
                LangFile.FormProtocolColor4 = IF->ReadString(SectionName,  "FormProtocolColor4",                        "Yellow");
                LangFile.FormProtocolColor5 = IF->ReadString(SectionName,  "FormProtocolColor5",                        "Blue");
                LangFile.FormProtocolColor6 = IF->ReadString(SectionName,  "FormProtocolColor6",                        "Claret");
                LangFile.FormProtocolColor7 = IF->ReadString(SectionName,  "FormProtocolColor7",                        "Navy");
                LangFile.FormProtocolColor8 = IF->ReadString(SectionName,  "FormProtocolColor8",                        "White");
                SectionName = "FormLEDSet";
                LangFile.FormLEDSetCaption        = IF->ReadString(SectionName, "FormLEDSetCaption", "LED Signal Setting");
                LangFile.FormLEDSetLabel1         = IF->ReadString(SectionName, "FormLEDSetLabel1", "Memo :");
                LangFile.FormLEDSetLabel2         = IF->ReadString(SectionName, "FormLEDSetLabel2", "Signal Type");
                LangFile.FormLEDSetLabel3         = IF->ReadString(SectionName, "FormLEDSetLabel3", "After Delay Setting");
                SectionName = "FormFont";
                LangFile.FormFontCaption      = IF->ReadString(SectionName, "FormFontCaption",     "Font Setting");
                LangFile.FormFontNameCaption  = IF->ReadString(SectionName, "FormFontNameCaption", "Font Name Setting");
                LangFile.FormFontLabel1       = IF->ReadString(SectionName, "FormFontLabel1",      "Font");
                LangFile.FormFontLabel2       = IF->ReadString(SectionName, "FormFontLabel2",      "File Path");
                LangFile.FormFontLabel3       = IF->ReadString(SectionName, "FormFontLabel3",      "File Path");
                LangFile.FormFontLabel4       = IF->ReadString(SectionName, "FormFontLabel4",      "File Path");
                LangFile.FormFontLabel5       = IF->ReadString(SectionName, "FormFontLabel5",      "File Path");
                LangFile.FormFontLabel6       = IF->ReadString(SectionName, "FormFontLabel6",      "Font Type");
                LangFile.FormFontLabel7       = IF->ReadString(SectionName, "FormFontLabel7",      "Font FileName");
                LangFile.FormFontItem[0]      = IF->ReadString(SectionName, "FormFontItem1",       "English(ASCII)");
                LangFile.FormFontItem[1]      = IF->ReadString(SectionName, "FormFontItem2",       "UniCode Wansung");
                LangFile.FormFontItem[2]      = IF->ReadString(SectionName, "FormFontItem3",       "UniCode Japanese");
                LangFile.FormFontItem[3]      = IF->ReadString(SectionName, "FormFontItem4",       "UniCode Chinese");
                LangFile.FormFontItem[4]      = IF->ReadString(SectionName, "FormFontItem5",       "Johap");
                LangFile.FormFontItem[5]      = IF->ReadString(SectionName, "FormFontItem6",       "UserFont");
                LangFile.FormFontItem[6]      = IF->ReadString(SectionName, "FormFontItem7",       "UniCode(All)");
                SectionName = "FormTransfer";
                LangFile.FormTransferCaption1 = IF->ReadString(SectionName, "FormTransferCaption1", "Start Transfer");
                LangFile.FormTransferCaption2 = IF->ReadString(SectionName, "FormTransferCaption2", "End Transfer");
                LangFile.FormTransferCaption3 = IF->ReadString(SectionName, "FormTransferCaption3", "Transfering...");
                LangFile.FormTransferLabel1   = IF->ReadString(SectionName, "FormTransferLabel1",   "Total files Rate");
                LangFile.FormTransferLabel2   = IF->ReadString(SectionName, "FormTransferLabel2",   "Single file Rate");
                SectionName = "FormLog";
                LangFile.FormLog = IF->ReadString(SectionName, "FormLog", "Communication Log");
                SectionName = "FormETC";
                LangFile.FormETC = IF->ReadString(SectionName, "FormETC", "Special Functions");
                SectionName = "FormFirmware";
                LangFile.FormFirmware = IF->ReadString(SectionName, "FormFirmware", "Firmware Upgrade");
                SectionName = "FormASCIISet";
                LangFile.FormASCIISet = IF->ReadString(SectionName, "FormASCIISet", "Set Message Properties");
                SectionName = "FormDABITOption";
                LangFile.FormDABITOption = IF->ReadString(SectionName, "FormDABITOption", "Board Functions");
                SectionName = "FormBluetooth";
                LangFile.FormBluetoothCaption = IF->ReadString(SectionName, "FormBluetoothCaption", "Set Bluetooth");
                LangFile.FormBluetoothLabel1 = IF->ReadString(SectionName,  "FormBluetoothLabel1",  "Bluetooth Name");
                LangFile.FormBluetoothLabel2 = IF->ReadString(SectionName,  "FormBluetoothLabel2",  "Bluetooth Password");
                LangFile.FormBluetoothLabel3 = IF->ReadString(SectionName,  "FormBluetoothLabel3",  "Set DelayTime(ms)");
                LangFile.FormBluetoothButton1 = IF->ReadString(SectionName,  "FormBluetoothButton1",  "Set");
                LangFile.FormBluetoothButton2 = IF->ReadString(SectionName,  "FormBluetoothButton2",  "Search");
                LangFile.FormBluetoothButton3 = IF->ReadString(SectionName,  "FormBluetoothButton3",  "Set");
                LangFile.FormBluetoothButton4 = IF->ReadString(SectionName,  "FormBluetoothButton4",  "BeginBLE");
                LangFile.FormBluetoothButton5 = IF->ReadString(SectionName,  "FormBluetoothButton5",  "EndBLE");
                SectionName = "Effect";
                LangFile.NoEffect     = IF->ReadString(SectionName, "NoEffect",     "None");
                LangFile.EffectType1  = IF->ReadString(SectionName, "EffectType1",  "Stop");
                LangFile.EffectType2  = IF->ReadString(SectionName, "EffectType2",  "Random");
                LangFile.EffectType3  = IF->ReadString(SectionName, "EffectType3",  "Shift");
                LangFile.EffectType4  = IF->ReadString(SectionName, "EffectType4",  "Wipe");
                LangFile.EffectType5  = IF->ReadString(SectionName, "EffectType5",  "Blind");
                LangFile.EffectType6  = IF->ReadString(SectionName, "EffectType6",  "Curtain");
                LangFile.EffectType7  = IF->ReadString(SectionName, "EffectType7",  "ZoomOut");
                LangFile.EffectType8  = IF->ReadString(SectionName, "EffectType8",  "ZoomIn");
                LangFile.EffectType9  = IF->ReadString(SectionName, "EffectType9",  "Rotate");
                LangFile.EffectType10 = IF->ReadString(SectionName, "EffectType10", "Blink B.G.");
                LangFile.EffectType11 = IF->ReadString(SectionName, "EffectType11", "Blink Text");
                LangFile.EffectType12 = IF->ReadString(SectionName, "EffectType12", "3D Effect");
                LangFile.EffectType13 = IF->ReadString(SectionName, "EffectType13", "Test");
                LangFile.EffectType14 = IF->ReadString(SectionName, "EffectType14", "");
                LangFile.EffectType15 = IF->ReadString(SectionName, "EffectType15", "");
                LangFile.EffectType16 = IF->ReadString(SectionName, "EffectType16", "");
                LangFile.EffectType17 = IF->ReadString(SectionName, "EffectType17", "");
                LangFile.EffectType18 = IF->ReadString(SectionName, "EffectType18", "");
                LangFile.EffectType19 = IF->ReadString(SectionName, "EffectType19", "");
                LangFile.EffectType20 = IF->ReadString(SectionName, "EffectType20", "");

                LangFile.DirType1  = IF->ReadString(SectionName, "DirType1",  "None");
                LangFile.DirType2  = IF->ReadString(SectionName, "DirType2",  "BrightOn");
                LangFile.DirType3  = IF->ReadString(SectionName, "DirType3",  "BirghtOff");
                LangFile.DirType4  = IF->ReadString(SectionName, "DirType4",  "HoriMirror");
                LangFile.DirType5  = IF->ReadString(SectionName, "DirType5",  "VertMirror");
                LangFile.DirType6  = IF->ReadString(SectionName, "DirType6",  "Left");
                LangFile.DirType7  = IF->ReadString(SectionName, "DirType7",  "Right");
                LangFile.DirType8  = IF->ReadString(SectionName, "DirType8",  "Up");
                LangFile.DirType9  = IF->ReadString(SectionName, "DirType9",  "Down");
                LangFile.DirType10 = IF->ReadString(SectionName, "DirType10", "Left Up");
                LangFile.DirType11 = IF->ReadString(SectionName, "DirType11", "Up&Down");
                LangFile.DirType12 = IF->ReadString(SectionName, "DirType12", "Left to Add");
                LangFile.DirType13 = IF->ReadString(SectionName, "DirType13", "Left Down");
                LangFile.DirType14 = IF->ReadString(SectionName, "DirType14", "Right Up");
                LangFile.DirType15 = IF->ReadString(SectionName, "DirType15", "Right Down");
                LangFile.DirType16 = IF->ReadString(SectionName, "DirType16", "Center");
                LangFile.DirType17 = IF->ReadString(SectionName, "DirType17", "Hori.Side");
                LangFile.DirType18 = IF->ReadString(SectionName, "DirType18", "Hori.Center");
                LangFile.DirType19 = IF->ReadString(SectionName, "DirType19", "Vert.Side");
                LangFile.DirType20 = IF->ReadString(SectionName, "DirType20", "Vert.Center");
                LangFile.DirType21 = IF->ReadString(SectionName, "DirType21", "Hori.Side&Center");
                LangFile.DirType22 = IF->ReadString(SectionName, "DirType22", "Vert.Side&Center");
                LangFile.DirType23 = IF->ReadString(SectionName, "DirType23", "Counter1");
                LangFile.DirType24 = IF->ReadString(SectionName, "DirType24", "Clockwize1");
                LangFile.DirType25 = IF->ReadString(SectionName, "DirType25", "Counter2");
                LangFile.DirType26 = IF->ReadString(SectionName, "DirType26", "Clockwize2");
                LangFile.DirType27 = IF->ReadString(SectionName, "DirType27", "Red");
                LangFile.DirType28 = IF->ReadString(SectionName, "DirType28", "Green");
                LangFile.DirType29 = IF->ReadString(SectionName, "DirType29", "Blue");
                LangFile.DirType30 = IF->ReadString(SectionName, "DirType30", "Yellow");
                LangFile.DirType31 = IF->ReadString(SectionName, "DirType31", "All(Sequentially)");
                LangFile.DirType32 = IF->ReadString(SectionName, "DirType32", "All(Concurrently)");
                LangFile.DirType33 = IF->ReadString(SectionName, "DirType33", "Sequential");
                LangFile.DirType34 = IF->ReadString(SectionName, "DirType34", "Random");
                LangFile.DirType35 = IF->ReadString(SectionName, "DirType35", "None");
                LangFile.DirType36 = IF->ReadString(SectionName, "DirType36", "Clear");
                LangFile.DirType37 = IF->ReadString(SectionName, "DirType37", "ShiftLeft");
                LangFile.DirType38 = IF->ReadString(SectionName, "DirType38", "ShiftRight");
                LangFile.DirType39 = IF->ReadString(SectionName, "DirType39", "ShiftUp");
                LangFile.DirType40 = IF->ReadString(SectionName, "DirType40", "ShiftDown");
                LangFile.DirType41 = IF->ReadString(SectionName, "DirType41", "ClearNone");
                LangFile.DirType42 = IF->ReadString(SectionName, "DirType42", "ClearLeft");
                LangFile.DirType43 = IF->ReadString(SectionName, "DirType43", "ClearRight");
                LangFile.DirType44 = IF->ReadString(SectionName, "DirType44", "ClearUp");
                LangFile.DirType45 = IF->ReadString(SectionName, "DirType45", "ClearDown");
                LangFile.DirType46 = IF->ReadString(SectionName, "DirType46", "ScanMode");
                LangFile.DirType47 = IF->ReadString(SectionName, "DirType47", "");
                LangFile.DirType48 = IF->ReadString(SectionName, "DirType48", "");
                LangFile.DirType49 = IF->ReadString(SectionName, "DirType49", "");
                LangFile.DirType50 = IF->ReadString(SectionName, "DirType50", "");
                SectionName = "Message";
                LangFile.MessageCaption = IF->ReadString(SectionName,  "MessageCaption",  "Message Window");
                LangFile.MessageButton01 = IF->ReadString(SectionName, "MessageButton01", "Yes");
                LangFile.MessageButton02 = IF->ReadString(SectionName, "MessageButton02", "No");
                LangFile.MessageButton03 = IF->ReadString(SectionName, "MessageButton03", "Cancel");
                LangFile.MessageButton04 = IF->ReadString(SectionName, "MessageButton04", "Confirm");
                LangFile.MessageButton05 = IF->ReadString(SectionName, "MessageButton05", "Save");
                LangFile.MessageButton06 = IF->ReadString(SectionName, "MessageButton06", "Whole");
                LangFile.MessageButton07 = IF->ReadString(SectionName, "MessageButton07", "Part");
                LangFile.MessageButton08 = IF->ReadString(SectionName, "MessageButton08", "Text");
                LangFile.MessageButton09 = IF->ReadString(SectionName, "MessageButton09", "Graphic");
                LangFile.MessageButton10 = IF->ReadString(SectionName, "MessageButton10", "Close");
                LangFile.MessageButton11 = IF->ReadString(SectionName, "MessageButton11", "Save & Close");
                LangFile.MessageButton12 = IF->ReadString(SectionName, "MessageButton12", "Dupe 1th List");
                LangFile.MessageButton13 = IF->ReadString(SectionName, "MessageButton13", "Dupe Group");
                LangFile.MessageButton14 = IF->ReadString(SectionName, "MessageButton14", "Cancel Auto");
                LangFile.MessageButton15 = IF->ReadString(SectionName, "MessageButton15", "Read");
                LangFile.MessageButton16 = IF->ReadString(SectionName, "MessageButton16", "None");
                LangFile.MessageButton17 = IF->ReadString(SectionName, "MessageButton17", "Set");
                LangFile.MessageButton18 = IF->ReadString(SectionName, "MessageButton18", "Clear");
                LangFile.MessageButton19 = IF->ReadString(SectionName, "MessageButton19", "Create");
                LangFile.MessageButton20 = IF->ReadString(SectionName, "MessageButton20", "Stop");
                LangFile.MessageButton21 = IF->ReadString(SectionName, "MessageButton21", "Open");
                LangFile.MessageButton22 = IF->ReadString(SectionName, "MessageButton22", "Send Auto");
                LangFile.MessageButton23 = IF->ReadString(SectionName, "MessageButton23", "Count");
                LangFile.MessageButton24 = IF->ReadString(SectionName, "MessageButton24", "Total");
                LangFile.Message01       = IF->ReadString(SectionName, "Message01", "Fastest");
                LangFile.Message02       = IF->ReadString(SectionName, "Message02", "Slowest");
                LangFile.Message03       = IF->ReadString(SectionName, "Message03", "Power On");
                LangFile.Message04       = IF->ReadString(SectionName, "Message04", "Power Off");
                LangFile.Message05       = IF->ReadString(SectionName, "Message05", "Can't connect");
                LangFile.Message06       = IF->ReadString(SectionName, "Message06", "Setting Time synchronization");
                LangFile.Message07       = IF->ReadString(SectionName, "Message07", "Setting DABIT Screen Size");
                LangFile.Message08       = IF->ReadString(SectionName, "Message08", "Clear Message Memory");
                LangFile.Message09       = IF->ReadString(SectionName, "Message09", "Message Register");
                LangFile.Message10       = IF->ReadString(SectionName, "Message10", "Select B.G. Image");
                LangFile.Message11       = IF->ReadString(SectionName, "Message11", "The command does not exist.");
                LangFile.Message12       = IF->ReadString(SectionName, "Message12", "This function is not supported by the firmware of DABIT.");
                LangFile.Message13       = IF->ReadString(SectionName, "Message13", "Transmitted data value is not normal.");
                LangFile.Message14       = IF->ReadString(SectionName, "Message14", "File Path :");
                LangFile.Message15       = IF->ReadString(SectionName, "Message15", "Can't read file.");
                LangFile.Message16       = IF->ReadString(SectionName, "Message16", "This file is not the firmware for the DABIT.");
                LangFile.Message17       = IF->ReadString(SectionName, "Message17", "Clear the Page Message");
                LangFile.Message18       = IF->ReadString(SectionName, "Message18", "Can not select over 3,145,727 bytes of font size");
                LangFile.Message19       = IF->ReadString(SectionName, "Message19", "Check the font size!");
                LangFile.Message20       = IF->ReadString(SectionName, "Message20", "Enable can affect communication speed. Do you want to continue?");
            }
        }
        __finally
        {
            if(IF)
            {
                delete IF;
                IF = NULL;
            }
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::InputLang()
{
        // 언어 적용 및 저장된 변수 적용 함수
        //this->Caption       = LangFile.FormProtocolCaption;
        Label1->Caption     = LangFile.FormProtocolLabel1;
        Label2->Caption     = LangFile.FormProtocolLabel2;
        Label3->Caption     = LangFile.FormProtocolLabel3;
        Label4->Caption     = LangFile.FormProtocolLabel4;
        Label5->Caption     = LangFile.FormProtocolLabel5;
        Label6->Caption     = LangFile.FormProtocolLabel6 + "(Pixel)";
        Label21->Caption    = LangFile.FormFontLabel6;
        Label7->Caption     = LangFile.FormProtocolLabel7;
        Label8->Caption     = LangFile.FormProtocolLabel8;
        Label9->Caption     = LangFile.FormProtocolLabel9;
        Label10->Caption    = LangFile.FormProtocolLabel10;
        Label11->Caption    = LangFile.FormProtocolLabel11;
        Label12->Caption    = LangFile.FormProtocolLabel12 + "(Pixel)";
        Label13->Caption    = LangFile.FormProtocolLabel13 + "(Pixel)";
        Label14->Caption    = LangFile.FormProtocolLabel14 + "(Pixel)";
        Label15->Caption    = LangFile.FormProtocolLabel15 + "(Pixel)";
        Label16->Caption    = LangFile.FormProtocolLabel16;
        Label17->Caption    = LangFile.FormProtocolLabel17;
        Label18->Caption    = LangFile.FormProtocolLabel18;
        Label19->Caption    = LangFile.FormProtocolLabel19;
        Label20->Caption    = LangFile.FormProtocolLabel28;

        Label23->Caption    = LangFile.FormProtocolEffect11;
        Label24->Caption    = LangFile.FormProtocolEffect11;

        SpeedButtonPreview->Caption     = LangFile.SpeedButtonPreview;
        SBASCIIPreview->Caption     = LangFile.SpeedButtonPreview;

        tsEnv->Caption             = LangFile.FormProtocolTab1;
        tsASCIIMessage->Caption    = LangFile.FormProtocolTab2;
        tsInfor->Caption           = LangFile.FormProtocolTab3;
        laSettings->Caption        = LangFile.BtnSetting;
        BtnPacketRead->Caption     = LangFile.BtnPacketRead;
        tsSpecial->Caption         = LangFile.BtnETC;
        NComPort->Caption          = LangFile.BtnPort;
        DataSend->Caption          = LangFile.BtnSend;
        BitBtnExit->Caption        = LangFile.BtnClose;

        pnLogCaption->Caption      = " " + LangFile.BtnLog;
        btnAdd->Caption            = LangFile.BtnAdd;
        btnDelete->Caption         = LangFile.BtnDelete;
        TTntSpeedButton *BtnSave;
        TTntPanel *pnBtnButton;
        for(int i = 1; i <= 10; i++)
        {
            BtnSave = dynamic_cast<TTntSpeedButton *>(FindComponent("btnSave" + IntToStr(i)));
            if(BtnSave)
                BtnSave->Caption = LangFile.BtnSave;
            BtnSave = dynamic_cast<TTntSpeedButton *>(FindComponent("ASCIISend" + IntToStr(i)));
            if(BtnSave)
                BtnSave->Caption = LangFile.BtnSend;
        }
        //laDefault->Caption         = "Defaut Attribute for displaying Text Message:\r\n    /P0000 /D9901 /F0003 /E0101 /S2002 /X0000 /Y0000 /B000\r\n    /C3/G0(0:None 1:Red 2:Green 3:Yellow 4:Blue 5:Purple 6:SkyBlue 7:White)";
        laDefault->Caption         = "/Cx,Gx(0:None 1:Red 2:Green 3:Yellow 4:Blue 5:Purple 6:SkyBlue 7:White)";

        TntLabel1->Caption        = FormMain->LangFile.FormProtocolLabel30;
        TntLabel2->Caption        = FormMain->LangFile.FormProtocolLabel31;
        TntLabel3->Caption        = FormMain->LangFile.FormProtocolLabel32;
        TntLabel10->Caption       = FormMain->LangFile.FormProtocolLabel34;
        //gbSignSize->Caption       = FormMain->LangFile.BtnSize;
        laSignSize->Caption       = FormMain->LangFile.BtnSize;
        //laProtocol->Caption       = gbSignSize->Caption;
        BtnSignSize->Caption      = FormMain->LangFile.BtnSend;
        BtnFontSend->Caption      = FormMain->LangFile.BtnFontSend;
        BtnLEDSetting->Caption    = FormMain->LangFile.BtnLEDSetting;
        BtnDIBDDownload->Caption  = FormMain->LangFile.BtnDIBDDownload;
        BtnDABITOption->Caption   = FormMain->LangFile.BtnDABITOptionSetting;
        BtnETC->Caption           = FormMain->LangFile.BtnETC;
        BtnLog->Caption           = FormMain->LangFile.BtnLog;
        BtnComPort->Caption       = FormMain->LangFile.BtnPort;
        BtnApply->Caption         = FormMain->LangFile.BtnApply;
        BtnFactoryReset->Caption  = FormMain->LangFile.BtnPSet;

        laProgram->Caption      = LangFile.FormProtocolLabel35;
        laProtocol->Caption     = LangFile.BtnProtocol;
        rgHex->Caption          = LangFile.FormProtocolTab1;
        rgASCII->Caption        = LangFile.FormProtocolTab2;
        //gbMemInput->Caption     = LangFile.FormProtocolLabel21;
        laMemInput->Caption     = LangFile.FormProtocolLabel20;
        TntLabel12->Caption     = LangFile.FormProtocolLabel21;
        TntLabel13->Caption     = LangFile.BtnErase;
        //gbBGImage->Caption      = LangFile.FormProtocolLabel22;
        laBGImage->Caption      = LangFile.FormProtocolLabel22;
        laScreen->Caption       = LangFile.BtnScreen;
        laRealTime->Caption     = LangFile.FormProtocolLabel28 + " " + LangFile.MessageButton17;
        //gbMsgErase->Caption     = LangFile.FormProtocolLabel20;
        //laMsgErase->Caption     = LangFile.FormProtocolLabel20;
        //gbSync->Caption         = LangFile.BtnSignalSend;
        laSync->Caption            = LangFile.BtnSignalSend;
        //gbBright->Caption          = LangFile.FormProtocolLabel33;
        laBright->Caption          = LangFile.FormProtocolLabel33;
        BtnMemInput->Caption       = LangFile.BtnSend;
        BtnMsgMemClear->Caption    = LangFile.BtnSend;
        BtnBGImage->Caption        = LangFile.BtnSend;
        BtnSendSync->Caption       = LangFile.BtnSend;
        BtnScreen->Caption         = LangFile.BtnSend;
        BtnRealTime->Caption       = LangFile.BtnSend;
        BitBtnPowerLEDOn->Caption  = LangFile.BtnPowerLEDOn;
        BitBtnPowerLEDOff->Caption = LangFile.BtnPowerLEDOff;
        BtnMsgErase->Caption       = LangFile.BtnErase;
        BtnReset->Caption          = LangFile.BtnReset;
        BtnFReset->Caption         = LangFile.BtnFReset;
        BtnBright->Caption         = LangFile.BtnSend;
        BtnTimeRead->Caption       = LangFile.BtnTimeRead;
        BtnTimeSet->Caption        = LangFile.BtnSyncLED;
        laLanguage->Caption        = LangFile.BtnLanguage;
        laAddress->Caption         = LangFile.FormComportAddress;

        int tempIndex = cbProtocol->ItemIndex;
        cbProtocol->Items->Strings[0] = LangFile.FormProtocolTab2;
        cbProtocol->Items->Strings[1] = LangFile.FormProtocolTab1;
        cbProtocol->ItemIndex = tempIndex;

        tempIndex = cbBGISelect->ItemIndex;
        cbBGISelect->Items->Strings[0] = LangFile.FormProtocolEffect1;
        cbBGISelect->ItemIndex = tempIndex;

        tempIndex = cbMsgInput->ItemIndex;
        cbMsgInput->Clear();
        for(int i = 0; i < 10; i++)
        {
            if(iLanguage == 0)
                cbMsgInput->Items->Add(IntToStr(i + 1) + " " + LangFile.MessageButton23);
            else
                cbMsgInput->Items->Add(LangFile.MessageButton24 + " " + IntToStr(i + 1));
        }
        cbMsgInput->ItemIndex = tempIndex;
        tempIndex = cbMsgMemClear->ItemIndex;
        cbMsgMemClear->Clear();
        cbMsgMemClear->Items->Add(LangFile.FormProtocolEffect9);
        for(int i = 0; i < MessageCount; i++)
            cbMsgMemClear->Items->Add("Page " + IntToStr(i + 1));
        cbMsgMemClear->ItemIndex = tempIndex;
        tempIndex = cbDirection->ItemIndex;
        cbDirection->Items->Strings[0] = LangFile.FormProtocolDirectItem1;
        cbDirection->Items->Strings[1] = LangFile.FormProtocolDirectItem2;
        cbDirection->Items->Strings[2] = LangFile.FormProtocolDirectItem3;
        cbDirection->Items->Strings[3] = LangFile.FormProtocolDirectItem4;
        cbDirection->Items->Strings[4] = LangFile.FormProtocolDirectItem5;
        cbDirection->Items->Strings[5] = LangFile.FormProtocolDirectItem6;
        cbDirection->ItemIndex = tempIndex;

        WideString asEffectType[24] = {LangFile.EffectType1, LangFile.EffectType2, LangFile.EffectType3, LangFile.EffectType4, LangFile.EffectType5,
            LangFile.EffectType6, LangFile.EffectType7, LangFile.EffectType8, LangFile.EffectType9, LangFile.EffectType10,
            LangFile.EffectType11, LangFile.EffectType12, LangFile.EffectType13,LangFile.EffectType14, LangFile.EffectType15,
            LangFile.EffectType16, LangFile.EffectType17, LangFile.EffectType18, LangFile.EffectType19, LangFile.EffectType20};
        for(int i = 0; i < EffectTypeCount; i++)
            EffectType[i] = asEffectType[i];

        WideString asDirType[50] = {LangFile.DirType1, LangFile.DirType2, LangFile.DirType3, LangFile.DirType4, LangFile.DirType5,
            LangFile.DirType6, LangFile.DirType7, LangFile.DirType8, LangFile.DirType9, LangFile.DirType10,
            LangFile.DirType11, LangFile.DirType12, LangFile.DirType13, LangFile.DirType14, LangFile.DirType15,
            LangFile.DirType16, LangFile.DirType17, LangFile.DirType18, LangFile.DirType19, LangFile.DirType20,
            LangFile.DirType21, LangFile.DirType22, LangFile.DirType23, LangFile.DirType24, LangFile.DirType25,
            LangFile.DirType26, LangFile.DirType27, LangFile.DirType28, LangFile.DirType29, LangFile.DirType30,
            LangFile.DirType31, LangFile.DirType32, LangFile.DirType33, LangFile.DirType34, LangFile.DirType35,
            LangFile.DirType36, LangFile.DirType37, LangFile.DirType38, LangFile.DirType39, LangFile.DirType40,
            LangFile.DirType41, LangFile.DirType42, LangFile.DirType43, LangFile.DirType44, LangFile.DirType45,
            LangFile.DirType46, LangFile.DirType47, LangFile.DirType48, LangFile.DirType49, LangFile.DirType50};
        WideString wsTextColor[8] = {
            FormMain->LangFile.FormProtocolColor1,  FormMain->LangFile.FormProtocolColor2,  FormMain->LangFile.FormProtocolColor3,  FormMain->LangFile.FormProtocolColor4,
            FormMain->LangFile.FormProtocolColor5, FormMain->LangFile.FormProtocolColor6,  FormMain->LangFile.FormProtocolColor7,  FormMain->LangFile.FormProtocolColor8};

        if(ProgramType == e2BPP)
            asDirType[29] = LangFile.DirType30;
        else
        {
            asDirType[29] = LangFile.FormProtocolColor8;
            //wsTextColor[3] = LangFile.FormProtocolColor9;
        }
        for(int i = 0; i < DirTypeCount; i++)
            DirType[i] = asDirType[i];

        cbScreen->Clear();
        cbScreen->ItemIndex = -1;
        for(int i = 0; i < 8; i++)
            cbScreen->Items->Add(wsTextColor[i]);
        cbScreen->ItemIndex = 0;

        //cbRealTime->Items->Strings[0] = LangFile.FormProtocolLabel36;
        cbRealTime->Items->Strings[0] = LangFile.FormProtocolLabel37;
        cbRealTime->Items->Strings[1] = LangFile.FormProtocolLabel38;
        if(bRealTimeIndex >= 0)
            cbRealTime->ItemIndex = bRealTimeIndex;
        else
            cbRealTime->ItemIndex = 0;

        SpeedValue[0] = "0(" + LangFile.Message01 + ")";
        /*for(int i = 1; i <= 16; i++)
            SpeedValue[i] = IntToStr(i * 10);
        SpeedValue[17] = IntToStr(200);
        SpeedValue[18] = IntToStr(255) + "(" + LangFile.Message02 + ")";*/
        SpeedValue[1] = IntToStr(5);
        SpeedValue[2] = IntToStr(10);
        SpeedValue[3] = IntToStr(15);
        SpeedValue[4] = IntToStr(20);
        SpeedValue[5] = IntToStr(25);
        SpeedValue[6] = IntToStr(30);
        SpeedValue[7] = IntToStr(35);
        SpeedValue[8] = IntToStr(40);
        SpeedValue[9] = IntToStr(45);
        SpeedValue[10] = IntToStr(50);
        SpeedValue[11] = IntToStr(55);
        SpeedValue[12] = IntToStr(60);
        SpeedValue[13] = IntToStr(70);
        SpeedValue[14] = IntToStr(80);
        SpeedValue[15] = IntToStr(100);
        SpeedValue[16] = IntToStr(120);
        SpeedValue[17] = IntToStr(140);
        SpeedValue[18] = IntToStr(160);
        SpeedValue[19] = IntToStr(180);
        SpeedValue[20] = IntToStr(200);
        SpeedValue[21] = IntToStr(255) + "(" + LangFile.Message02 + ")";
        DelayValue[0] = "0";
        DelayValue[1] = "2";
        DelayValue[2] = "4";
        DelayValue[3] = "8";
        DelayValue[4] = "10";
        DelayValue[5] = "15";
        DelayValue[6] = "20";
        DelayValue[7] = "30";
        DelayValue[8] = "40";
        DelayValue[9] = "60";
        DelayValue[10] = "80";
        DelayValue[11] = "120(2Min)";
        DelayValue[12] = "180(3Min)";
        DelayValue[13] = "300(5Min)";
        DelayValue[14] = "600(10Min)";
        DelayValue[15] = "1800(30Min)";
        DelayValue[16] = "3600(1Hour)";
        DelayValue[17] = "10800(3Hour)";
        DelayValue[18] = "18000(5Hour)";
        DelayValue[19] = "32400(9Hour)";

        cbDispControl->Clear();
        cbDispControl->ItemIndex = -1;
        cbDispControl->Items->Add("OFF");
        cbDispControl->Items->Add("ON");
        for(int i = 1; i <= 10; i++)
        {
            cbDispControl->Items->Add(IntToStr(i));
        }
        cbDispControl->Items->Add("20");
        cbDispControl->Items->Add("30");
        cbDispControl->Items->Add("40");
        cbDispControl->Items->Add("50");
        cbDispControl->Items->Add("60");
        cbDispControl->Items->Add("70");
        cbDispControl->Items->Add("80");
        cbDispControl->Items->Add("90");
        cbDispControl->ItemIndex = 0;

        rbPageNo1->Caption = LangFile.FormProtocolLabel28;
        rbPageNo2->Caption = LangFile.FormProtocolLabel29;
        cbPageNo->Clear();
        cbPageNo->ItemIndex = -1;
        for(int i = 0; i < MessageCount; i++)
        {
            cbPageNo->Items->Add(IntToStr(i + 1));
        }
        cbPageNo->ItemIndex = 0;
        cbPageNo->ItemIndex = ProtocolPageNo;

        cbAssistEffect->Clear();
        cbAssistEffect->Items->Add(LangFile.FormProtocolEffect1);
        cbAssistEffect->ItemIndex = 0;

        cbCodeKind->Clear();
        cbCodeKind->Items->Add(LangFile.FormProtocolEffect5);
        cbCodeKind->Items->Add(LangFile.FormProtocolEffect6);
        cbCodeKind->ItemIndex = 0;

        cbFontSize->Items->Clear();
        for(int i = 4; i <= 16; i++)
        {
            if(i == 4)
                cbFontSize->Items->Add(IntToStr(i * 4) + "(Standard)");
            else
                cbFontSize->Items->Add(IntToStr(i * 4));
        }
        cbFontSize->ItemIndex = 0;
        cbFontKind->ItemIndex = 0;

        cbEntEffect->Clear();
        cbLasEffect->Clear();
        cbLasEffect->Items->Add(LangFile.NoEffect);
        for(int i = 0; i < EffectTypeCount - 1; i++)
        {
            if(i == etScaleIn)
                continue;
            cbEntEffect->Items->Add(EffectType[i]);
            cbLasEffect->Items->Add(EffectType[i]);
        }
        cbEntEffect->ItemIndex = 0;
        cbLasEffect->ItemIndex = 0;
        cbEntDirect->Text = DirType[0];
        cbLasDirect->Text = LangFile.NoEffect;

        cbSpeed->Clear();
        for(int i = 0; i < 22; i++)
        {
            cbSpeed->Items->Add(SpeedValue[i]);
        }
        cbSpeed->ItemIndex = 0;

        cbDelay->Clear();
        for(int i = 0; i < 20; i++)
        {
            cbDelay->Items->Add(DelayValue[i]);
        }
        cbDelay->ItemIndex = 1;

        cbStartXPos->ItemIndex = -1;
        cbEndXPos->ItemIndex = -1;
        cbStartXPos->Clear();
        cbEndXPos->Clear();
        for(int i = 0; i <= ModuleWidth * 4; i++)
        {
            cbStartXPos->Items->Add(IntToStr(i * 4));
            cbEndXPos->Items->Add(IntToStr(i * 4));
        }
        cbStartXPos->ItemIndex = 0;
        cbEndXPos->ItemIndex = cbEndXPos->Items->Count - 1;

        cbStartYPos->ItemIndex = -1;
        cbEndYPos->ItemIndex = -1;
        cbStartYPos->Clear();
        cbEndYPos->Clear();
        for(int i = 0; i <= ModuleHeight * 4; i++)
        {
            cbStartYPos->Items->Add(IntToStr(i * 4));
            cbEndYPos->Items->Add(IntToStr(i * 4));
        }
        cbStartYPos->ItemIndex = 0;
        cbEndYPos->ItemIndex = cbEndYPos->Items->Count - 1;

        cbBGImage->Clear();
        cbBGImage->Items->Add(LangFile.FormProtocolEffect1);
        for(int i = 1; i <= 10; i++)
        {
            cbBGImage->Items->Add(IntToStr(i));
        }
        cbBGImage->ItemIndex = 0;
}
//---------------------------------------------------------------------------

int __fastcall TFormMain::Message(WideString Mes, WideString wsYes, WideString wsNo, WideString wsCancel, int Width, int Height, int btnCnt, TColor FontColor)
{
        // 메시지 폼 함수
        formMessage = new TFormMessage(this);
        formMessage->Caption = LangFile.MessageCaption;
        formMessage->pnCaption->Caption = " " + formMessage->Caption;
        if(FontColor == clBlack)
            formMessage->laMessage->Font->Color = TextColor;
        else
            formMessage->laMessage->Font->Color = FontColor;
        formMessage->laMessage->Caption = Mes;
        int tempWidth = 0;
        int tempHeight = 0;
        if((Width * 10) * 2 > 15 * 10 * 2)
        {
            if((Width * 10) * 2 > 25 * 12 * 2)
                tempWidth = 25 * 12 * 2;
            else
                tempWidth = (Width * 10) * 2;
        }
        else
            tempWidth = 15 * 10 * 2;
        formMessage->Width = tempWidth;
        formMessage->pnMain->Width = tempWidth - 8;
        formMessage->laMessage->Width = formMessage->Width;
        formMessage->laMessage->Top = 18;
        formMessage->laMessage->Height = formMessage->laMessage->Top + Height * 13;
        tempHeight = 86 + formMessage->laMessage->Height;
        formMessage->Height = tempHeight;
        formMessage->pnMain->Height = tempHeight - 8;
        //formMessage->btnYes->Top = formMessage->laMessage->Top + formMessage->laMessage->Height + 12;
        //formMessage->btnNo->Top = formMessage->btnYes->Top;
        //formMessage->btnCancel->Top = formMessage->btnYes->Top;
        formMessage->pnbtnYes->Top = formMessage->laMessage->Top + formMessage->laMessage->Height + 12;
        formMessage->pnbtnNo->Top = formMessage->pnbtnYes->Top;
        formMessage->pnbtnCancel->Top = formMessage->pnbtnYes->Top;
        formMessage->laMessage->Alignment = (TAlignment)2;
        if(btnCnt == 1)
        {
            formMessage->pnbtnNo->Visible = false;
            formMessage->pnbtnCancel->Visible = false;
        }
        else if(btnCnt == 2)
            formMessage->pnbtnCancel->Visible = false;
        //formMessage->btnYes->Left = (formMessage->Width - ((formMessage->btnYes->Width * btnCnt) + (btnCnt * 10))) / 2;
        //formMessage->btnNo->Left = formMessage->btnYes->Left + formMessage->btnYes->Width + 10;
        //formMessage->btnCancel->Left = formMessage->btnNo->Left + formMessage->btnNo->Width + 10;
        formMessage->pnbtnYes->Left = (formMessage->Width - ((formMessage->btnYes->Width * btnCnt) + (btnCnt * 10))) / 2;
        formMessage->pnbtnNo->Left = formMessage->pnbtnYes->Left + formMessage->pnbtnYes->Width + 10;
        formMessage->pnbtnCancel->Left = formMessage->pnbtnNo->Left + formMessage->pnbtnNo->Width + 10;
        formMessage->btnYes->Caption = wsYes;
        formMessage->btnNo->Caption = wsNo;
        formMessage->btnCancel->Caption = wsCancel;
        formMessage->TabStop = true;
        formMessage->TabOrder = 1;
        formMessage->ShowModal();
        int Res = formMessage->Tag;
        if(formMessage)
        {
            delete formMessage;
            formMessage = NULL;
        }
        return Res;
}
//--------------------------------------------------------------------------

void __fastcall TFormMain::DelayCount(int Cnt)
{
        // 통신 리시브 딜레이 함수
        DWORD nowTick = GetTickCount();
        DWORD targetTick = nowTick + Cnt;
        while(nowTick < targetTick)
        {
            nowTick = GetTickCount();
            Application->ProcessMessages();
            if(SchBmpFile || bStopSend)
            {
                break;
            }
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::NoneDelayCount(int Cnt)
{
        // 딜레이 함수
        DWORD nowTick = GetTickCount();
        DWORD targetTick = nowTick + Cnt;
        while(nowTick < targetTick)
        {
            nowTick = GetTickCount();
            Application->ProcessMessages();
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::LANDelayCount(int Cnt)
{
        // LAN 통신 딜레이 함수
        DWORD nowTick = GetTickCount();
        DWORD targetTick = nowTick + Cnt;
        DWORD gap;
        bool bShow = false;
        TFormMessage *FMessage = NULL;
        while(nowTick < targetTick)
        {
            nowTick = GetTickCount();
            Application->ProcessMessages();
            gap = (targetTick - nowTick - 1) / 1000;
            if(this->Showing)
            {
                if((Cnt / 1000) - gap > 4 && !bShow)
                {
                    bShow = true;
                    FMessage = new TFormMessage(this);
                    FMessage->Caption = "Message";
                    FMessage->pnCaption->Caption = " " + FMessage->Caption;
                    FMessage->laMessage->Font->Color = clRed;
                    FMessage->laMessage->Font->Style = TFontStyles() << fsBold;
                    FMessage->laMessage->Caption = "\r\nConnecting Network. Please wait ...\r\n";
                    FMessage->Width = (17 * 12) * 2;
                    FMessage->pnMain->Width = FMessage->Width - 8;
                    FMessage->laMessage->Width = FMessage->Width;
                    FMessage->laMessage->Top = 18;
                    FMessage->laMessage->Height = FMessage->laMessage->Top + 4 * 13;
                    FMessage->Height = 56 + FMessage->laMessage->Height + FMessage->btnCancel->Height;
                    FMessage->pnMain->Height = FMessage->Height - 8;
                    FMessage->pnbtnYes->Top = FMessage->laMessage->Top + FMessage->laMessage->Height + 12;
                    FMessage->laMessage->Alignment = (TAlignment)2;
                    FMessage->pnbtnYes->Visible = false;
                    FMessage->pnbtnNo->Visible = false;
                    FMessage->btnCancel->Caption = "Cancel";
                    FMessage->pnbtnCancel->Top = FMessage->laMessage->Top + FMessage->laMessage->Height + 12;
                    FMessage->pnbtnCancel->Left = (FMessage->Width - FMessage->pnbtnCancel->Width) / 2;
                    FMessage->btnCancel->Visible = true;
                    FMessage->Show();
                    FMessage->Update();
                    FMessage->BringToFront();
                }
            }
            if(FMessage)
            {
                if(FMessage->Tag == mrCancel)
                   bStopSend = true;
                FMessage->laMessage->Caption = "\r\nConnecting Network. Please wait ...\r\n\r\n Time : " + IntToStr(gap + 1) + " Sec";
            }
            if(DNSConnect || bStopSend)
                break;
        }
        if(FMessage)
        {
            delete FMessage;
            FMessage = NULL;
        }
}
//---------------------------------------------------------------------------

bool __fastcall TFormMain::ModuleData(BYTE ComKind, BYTE Addr, BYTE *Data, DWORD DataLen, int Number, OPCode OPC, bool bMessage)
{
        // 일반 데이터 전송한 리시브 데이터 받고 판단하는 함수
        bool res = false;
        bCRCFail = false;
        bDataFail = false;
        asLogMessage = "";
        DWORD nowTick = 0;
        DWORD targetTick = 0;
        ZeroMemory(&RP, sizeof(RP));
        SchBmpFile = false;
        SendData(ComKind, Addr, Data, DataLen, Number, OPC);
        nowTick = GetTickCount();
        if(FormMain->DelayRun)
        {
            targetTick = nowTick + DelayTime;
            while(nowTick < targetTick)
            {
                nowTick = GetTickCount();
                Application->ProcessMessages();
            }
            res = true;
        }
        else
        {
            if(RetryCnt > 0)
                targetTick = GetTickCount() + WaitTime * 2;
            else
                targetTick = GetTickCount() + WaitTime;
            while(nowTick < targetTick)
            {
                nowTick = GetTickCount();
                Application->ProcessMessages();
                if(SchBmpFile)
                    break;
            }
            if(!SchBmpFile || bCRCFail || bDataFail)
            {
                AnsiString asMessage = "Transfer FAIL";
                if(bCRCFail)
                    asLogMessage += " [CRC FAIL]";
                if(bDataFail)
                {
                    if(FailData == 0x10)
                        asLogMessage = asLogMessage + " [" + FormMain->LangFile.Message10 + "]\r\n";
                    else if(FailData == 0x20)
                        asLogMessage = asLogMessage + " [" + FormMain->LangFile.Message11 + "]\r\n";
                    else if(FailData == 0x40)
                        asLogMessage = asLogMessage + " [" + FormMain->LangFile.Message12 + "]\r\n";
                    else
                        asLogMessage = asLogMessage + " 0x" + Byte2HexConvert(FailData) + " [Data FAIL]\r\n";
                }
                if(bMessage && !bCRCFail && !bDataFail)
                    Message("\r\n" + asMessage + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                res = false;
            }
            else
                res = true;
        }
        return res;
}
//---------------------------------------------------------------------------

bool __fastcall TFormMain::ModuleFontPacketData(BYTE ComKind, BYTE Add, OPCode OPC, BYTE *Data, int TotBlockCnt, int BlockCnt, int FontTotBlockCnt, int FontBlockCnt, int Number, int ImgCnt)
{
        // Font 데이터 전송한 리시브 데이터 받고 판단하는 함수
        ZeroMemory(&RP, sizeof(RP));
        SchBmpFile = false;
        bool res = false;
        bCRCFail = false;
        bDataFail = false;
        asLogMessage = "";
        asLogMessage = "";
        DWORD nowTick = 0;
        DWORD targetTick = 0;
        SendFontFileData(ComKind, Add, OPC, Data, TotBlockCnt, BlockCnt, FontTotBlockCnt, FontBlockCnt, Number, ImgCnt);
        if(FormMain->DelayRun)
        {
            targetTick = nowTick + DelayTime;
            while(nowTick < targetTick)
            {
                nowTick = GetTickCount();
                Application->ProcessMessages();
            }
            res = true;
        }
        else
        {
            if(RetryCnt > 0)
                targetTick = GetTickCount() + WaitTime * 2;
            else
                targetTick = GetTickCount() + WaitTime;
            while(nowTick < targetTick)
            {
                nowTick = GetTickCount();
                Application->ProcessMessages();
                if(SchBmpFile)
                    break;
            }
            if(!SchBmpFile || bCRCFail || bDataFail)
            {
                AnsiString asMessage = "Transfer FAIL";
                if(bCRCFail)
                    asLogMessage += " [CRC FAIL]";
                if(bDataFail)
                {
                    if(FailData == 0x10)
                        asLogMessage = asLogMessage + " [" + FormMain->LangFile.Message10 + "]\r\n";
                    else if(FailData == 0x20)
                        asLogMessage = asLogMessage + " [" + FormMain->LangFile.Message11 + "]\r\n";
                    else if(FailData == 0x40)
                        asLogMessage = asLogMessage + " [" + FormMain->LangFile.Message12 + "]\r\n";
                    else
                        asLogMessage = asLogMessage + " 0x" + Byte2HexConvert(FailData) + " [Data FAIL]\r\n";
                }
                res = false;
            }
            else
                res = true;
        }
        return res;
}
//---------------------------------------------------------------------------

bool __fastcall TFormMain::ModuleASCIIData(BYTE ComKind, BYTE Addr, BYTE *Data, DWORD DataLen, bool bMessage)
{
        // ASCII 데이터 전송한 리시브 데이터 받고 판단하는 함수
        bool res = false;
        bCRCFail = false;
        bDataFail = false;
        //asLogMessage = "";
        DWORD nowTick = 0;
        DWORD targetTick = 0;
        ZeroMemory(&RP, sizeof(RP));
        SchBmpFile = false;
        SendASCIIData(ComKind, Addr, Data, DataLen);
        nowTick = GetTickCount();
        if(FormMain->DelayRun)
        {
            targetTick = nowTick + DelayTime;
            while(nowTick < targetTick)
            {
                nowTick = GetTickCount();
                Application->ProcessMessages();
            }
            res = true;
        }
        else
        {
            int tempWaitTime = WaitTime;
            if(FComPort)
                tempWaitTime = 500;
            if(RetryCnt > 0)
                targetTick = GetTickCount() + tempWaitTime * 2;
            else
                targetTick = GetTickCount() + tempWaitTime;
            while(nowTick < targetTick)
            {
                nowTick = GetTickCount();
                Application->ProcessMessages();
                if(FormMain->SchBmpFile)
                    break;
            }
            if(!SchBmpFile || bCRCFail || bDataFail)
            {
                AnsiString asMessage = "Transfer FAIL";
                if(bMessage && !bCRCFail && !bDataFail)
                    Message("\r\n" + asMessage + "\r\n", LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                res = false;
            }
            else
                res = true;
        }
        return res;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::SendFontFileData(BYTE ComKind, BYTE Add, OPCode OPC, BYTE *Data, int TotBlockCnt, int BlockCnt, int FontTotBlockCnt, int FontBlockCnt, int Number, int ImgCnt)
{
        // Font 데이터 프로토콜 전송 함수
        char *TDBuff;
        union{
            int iSize;
            char cSize[4];
        }uSize;
        union{
            unsigned int uiData;
            char cData[2];
        }uData;
        int bccvalue = 2;
        BYTE *CRCData;
        int PacketSize = 0;
        int FontHeaderSize = 0;
        int tempSize = 0;
        int tempTotSize = 0;
        int iBlockCnt = 0;
        bool btempBCC = false;
        btempBCC = FlagCrcPacket;
        //if(ComKind == 0)
        //    btempBCC = true;
        //else
        //    btempBCC = false;
        if(btempBCC)
            bccvalue = 2;
        bool bComp = false;
        BYTE CompData[2048];
        ZeroMemory(CompData, sizeof(CompData));
        BYTE BFontHeaderData[16];
        ZeroMemory(BFontHeaderData, sizeof(BFontHeaderData));
        BFontHeaderData[0] = 0x50;
        BFontHeaderData[1] = 0x48;
        BFontHeaderData[2] = 0x53;
        BFontHeaderData[3] = 0x46;
        BFontHeaderData[4] = 0x4E;
        BFontHeaderData[5] = 0x54;
        BFontHeaderData[6] = 0x30;
        if(OPC == FFNT && BlockCnt == 0 && Number == 1)
            FontHeaderSize = 16;
        PacketSize = sizeof(sFontFileData) + FontHeaderSize;
        PacketSize *= 2;
        sFontFileData FontFileData;
        TDBuff = new char[PacketSize + iBlockCnt];
        ZeroMemory(TDBuff, PacketSize + iBlockCnt);
        FontFileData.SDLE = DLE;
        FontFileData.STX = STX;
        FontFileData.Add = Add;
        tempSize = 5 + sizeof(FontFileData.OPC) + sizeof(FontFileData.TotBlockCnt) + sizeof(FontFileData.BlockNum)
             + sizeof(FontFileData.FontBlockCnt) + sizeof(FontFileData.FontBlockNum) + sizeof(FontFileData.FileNum);
        uSize.iSize = tempSize - 5 + sizeof(FontFileData.Data) + FontHeaderSize;
        if(btempBCC)
            uSize.iSize += bccvalue;
        FontFileData.HLen = uSize.cSize[1];
        if(DelayRun)
            FontFileData.HLen |= 0x20;
        if(btempBCC)
            FontFileData.HLen |= 0x40;
        FontFileData.LLen = uSize.cSize[0];
        if(btempBCC)
            uSize.iSize -= bccvalue;
        FontFileData.OPC = OPC;
        FontFileData.TotBlockCnt[0] = (BYTE)TotBlockCnt;
        FontFileData.TotBlockCnt[1] = (BYTE)(TotBlockCnt >> 8);
        FontFileData.BlockNum[0] = (BYTE)BlockCnt;
        FontFileData.BlockNum[1] = (BYTE)(BlockCnt >> 8);
        FontFileData.FontBlockCnt[0] = (BYTE)FontTotBlockCnt;
        FontFileData.FontBlockCnt[1] = (BYTE)(FontTotBlockCnt >> 8);
        FontFileData.FontBlockNum[0] = (BYTE)FontBlockCnt;
        FontFileData.FontBlockNum[1] = (BYTE)(FontBlockCnt >> 8);
        FontFileData.FileNum = (BYTE)ImgCnt;
        FontFileData.EDLE = DLE;
        FontFileData.ETX = ETX;
        if(btempBCC)
        {
            CRCData = new BYTE[uSize.iSize * 2 + 3];
            ZeroMemory(CRCData, uSize.iSize * 2 + 3);
            CRCData[0] = FontFileData.Add;
            CRCData[1] = FontFileData.HLen;
            CRCData[2] = FontFileData.LLen;
            CRCData[3] = FontFileData.OPC;
            CRCData[4] = FontFileData.TotBlockCnt[0];
            CRCData[5] = FontFileData.TotBlockCnt[1];
            CRCData[6] = FontFileData.BlockNum[0];
            CRCData[7] = FontFileData.BlockNum[1];
            CRCData[8] = FontFileData.FontBlockCnt[0];
            CRCData[9] = FontFileData.FontBlockCnt[1];
            CRCData[10] = FontFileData.FontBlockNum[0];
            CRCData[11] = FontFileData.FontBlockNum[1];
            CRCData[12] = FontFileData.FileNum;
            if(OPC == FFNT && BlockCnt == 0 && Number == 1)
                memcpy(&CRCData[13], BFontHeaderData, FontHeaderSize);
            tempTotSize = sizeof(FontFileData.Data);
            memcpy(&CRCData[13 + FontHeaderSize], Data, tempTotSize);
            uData.uiData = Calc_crc(CRCData, uSize.iSize + 3);
            memcpy(&FontFileData.BCC, uData.cData, bccvalue);
            if(CRCData)
            {
                delete [] CRCData;
                CRCData = NULL;
            }
            memcpy(TDBuff, &FontFileData, tempSize);
            if(OPC == FFNT && FontBlockCnt == 0 && Number == 1)
                memcpy(&TDBuff[tempSize], BFontHeaderData, FontHeaderSize);
            memcpy(&TDBuff[tempSize + FontHeaderSize], Data, tempTotSize);
            memcpy(&TDBuff[tempSize + FontHeaderSize + tempTotSize], FontFileData.BCC, bccvalue);
            TDBuff[tempSize + FontHeaderSize + tempTotSize + bccvalue] = FontFileData.EDLE;
            TDBuff[tempSize + FontHeaderSize + tempTotSize + bccvalue + 1] = FontFileData.ETX;
            PacketSize = tempSize + FontHeaderSize + tempTotSize + bccvalue + 2;
        }
        else
        {
            memcpy(TDBuff, &FontFileData, tempSize);
            if(OPC == FFNT && FontBlockCnt == 0 && Number == 1)
                memcpy(&TDBuff[tempSize], BFontHeaderData, FontHeaderSize);
            tempTotSize = sizeof(FontFileData.Data);
            memcpy(&TDBuff[tempSize + FontHeaderSize], Data, tempTotSize);
            TDBuff[tempSize + FontHeaderSize + tempTotSize] = FontFileData.EDLE;
            TDBuff[tempSize + FontHeaderSize + tempTotSize + 1] = FontFileData.ETX;
            PacketSize = tempSize + FontHeaderSize + tempTotSize + 2;
        }
        asLogMessage = " ";
        if(BlockCnt <= 1 || bLogviewall)
        {
            for(int i = 0; i < PacketSize + iBlockCnt; i++)
                asLogMessage = asLogMessage + Byte2HexConvert(TDBuff[i]) + " ";
        }
        else
        {
            for(int i = 0; i < 30; i++)
                asLogMessage = asLogMessage + Byte2HexConvert(TDBuff[i]) + " ";
            asLogMessage = asLogMessage + "~ ";
            for(int i = PacketSize + iBlockCnt - 2; i < PacketSize; i++)
                asLogMessage = asLogMessage + Byte2HexConvert(TDBuff[i]) + " ";
        }
        try{
            if(FormMain->ComKind == 1 && DMClient)
            {
                if(!DMClient->ClientSocket1->Socket->Connected)
                {
                    DNSConnect = false;
                    DMClient->ClientSocket1->Open();
                    LANDelayCount(ServerCommOpenDelay);
                    if(DNSConnect)
                        DNSConnect = false;
                }
                DMClient->ClientSocket1->Socket->SendBuf(TDBuff, PacketSize + iBlockCnt);
            }
            else if(FormMain->ComKind == 2)
            {
                if(ServerClientSocket)
                {
                    if(ServerClientSocket->Connected)
                        ServerClientSocket->SendBuf(TDBuff,  PacketSize + iBlockCnt);
                }
            }
            else if(FormMain->ComKind == 3)
            {
                if(SH)
                {
                    //if(SH->Connect)
                        IdUDPServer->SendBuffer(asUDPIPAddress, DSPPort, TDBuff, PacketSize + iBlockCnt);
                }
            }
            else
            {
                if(!ComPort1->Connected)
                    ComPort1->Open();
                ComPort1->Write(TDBuff, PacketSize + iBlockCnt);
            }
        }catch(...){
        }
        if(TDBuff)
        {
            delete [] TDBuff;
            TDBuff  = NULL;
        }
}
//--------------------------------------------------------------------------

void __fastcall TFormMain::SendData(BYTE ComKind, BYTE Add, BYTE *Data, DWORD DataLen, int Number, OPCode OPC)
{
        // 일반 데이터 프로토콜 전송 함수
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
            tempBCC = FlagCrcPacket;
        if(tempBCC)
            bccvalue = 2;
        int CRCDLESize = 0;
        uSize.iSize = 1 + DataLen;
        CRCDLESize = uSize.iSize + bccvalue + 3;
        BYTE *CRCData = NULL;
        CRCData = new BYTE[CRCDLESize];
        ZeroMemory(CRCData, CRCDLESize);
        CRCData[0] = Add;
        if(tempBCC)
            uSize.iSize += 2;
        CRCData[1] = uSize.cSize[1];
        if(DelayRun)
            CRCData[1] |= 0x20;
        if(tempBCC)
            CRCData[1] |= 0x40;
        CRCData[2] = uSize.cSize[0];
        CRCData[3] = OPC;
        memcpy(&CRCData[4], Data, DataLen);
        if(tempBCC)
        {
            uData.uiData = Calc_crc(CRCData, CRCDLESize - bccvalue);
            memcpy(&CRCData[4 + DataLen], uData.cData, bccvalue);
        }
        TotDataLen = 8 + bccvalue + DataLen + cdSize;
        CDBuff = new char[TotDataLen];
        ZeroMemory(CDBuff, TotDataLen);

        CDBuff[0] = DLE;
        CDBuff[1] = STX;
        memcpy(&CDBuff[2], CRCData, CRCDLESize);
        CDBuff[2 + CRCDLESize + cdSize] = DLE;
        CDBuff[3 + CRCDLESize + cdSize] = ETX;
        asLogMessage = " ";
        if(Number <= 1 || bLogviewall)
        {
            for(int i = 0; i < TotDataLen; i++)
                asLogMessage = asLogMessage + Byte2HexConvert(CDBuff[i]) + " ";
        }
        else
        {
            for(int i = 0; i < 30; i++)
                asLogMessage = asLogMessage + Byte2HexConvert(CDBuff[i]) + " ";
            asLogMessage = asLogMessage + "~ ";
            for(int i = TotDataLen - 2; i < TotDataLen; i++)
                asLogMessage = asLogMessage + Byte2HexConvert(CDBuff[i]) + " ";
        }
        try{
            if(ComKind == 1 && DMClient)
            {
                if(!DMClient->ClientSocket1->Socket->Connected)
                {
                    DNSConnect = false;
                    DMClient->ClientSocket1->Open();
                    LANDelayCount(ServerCommOpenDelay);
                    if(DNSConnect)
                        DNSConnect = false;
                }
                DMClient->ClientSocket1->Socket->SendBuf(CDBuff, TotDataLen);
            }
            else if(ComKind == 2)
            {
                if(ServerClientSocket)
                {
                    if(ServerClientSocket->Connected)
                        ServerClientSocket->SendBuf(CDBuff, TotDataLen);
                }
            }
            else if(ComKind == 3)
            {
                if(SH)
                {
                    //if(SH->Connect)
                        IdUDPServer->SendBuffer(asUDPIPAddress, DSPPort, CDBuff, TotDataLen);
                }
            }
            else
            {
                if(!ComPort1->Connected)
                    ComPort1->Open();
                ComPort1->Write(CDBuff, TotDataLen);
            }
        }catch(...){
        }
        if(CRCData)
        {
            delete [] CRCData;
            CRCData = NULL;
        }
        if(CDBuff)
        {
            delete [] CDBuff;
            CDBuff = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::SendASCIIData(BYTE ComKind, BYTE Add, BYTE *Data, DWORD DataLen)
{
        // ASCII 데이터 프로토콜 전송 함수
        BYTE *CDBuff = new char[DataLen];
        ZeroMemory(CDBuff, DataLen);
        memcpy(CDBuff, Data, DataLen);
        if(PorotocolType == 1)
        {
            int bytePos = 0;
            AnsiString tempMessage = AnsiString((char*)CDBuff).SubString(1, DataLen);
            bytePos = tempMessage.Pos("/F01");
            if(ProtocolData.ProtocolCodeKind[11][ASCIIPageNo] == 1 || bytePos)
            {}
            else
                asLogMessage = " " + AnsiString((char*)CDBuff).SubString(1, DataLen);
        }
        else
        {
            asLogMessage = " ";
            for(int i = 0; i < DataLen; i++)
                asLogMessage = asLogMessage + Byte2HexConvert(CDBuff[i]) + " ";
        }
        try{
            if(ComKind == 1 && DMClient)
            {
                if(!DMClient->ClientSocket1->Socket->Connected)
                {
                    DNSConnect = false;
                    DMClient->ClientSocket1->Open();
                    LANDelayCount(ServerCommOpenDelay);
                    if(DNSConnect)
                        DNSConnect = false;
                }
                DMClient->ClientSocket1->Socket->SendBuf(CDBuff, DataLen);
            }
            else if(ComKind == 2)
            {
                if(ServerClientSocket)
                {
                    if(ServerClientSocket->Connected)
                        ServerClientSocket->SendBuf(CDBuff, DataLen);
                }
            }
            else if(ComKind == 3)
            {
                if(SH)
                {
                    //if(SH->Connect)
                        IdUDPServer->SendBuffer(asUDPIPAddress, DSPPort, CDBuff, DataLen);
                }
            }
            else
            {
                if(!ComPort1->Connected)
                    ComPort1->Open();
                ComPort1->Write(CDBuff, DataLen);
            }
        }catch(...){
        }
        if(CDBuff)
        {
            delete [] CDBuff;
            CDBuff = NULL;
        }
}
//---------------------------------------------------------------------------

unsigned short int __fastcall TFormMain::Calc_crc(unsigned char *buf, int numbytes)
{
      // CRC 함수
      unsigned char *ptr = buf, d;
      int i;
      int j;
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

AnsiString __fastcall TFormMain::Byte2HexConvert(BYTE Value)
{
        // Byte 값을 로 String Hex 변환 함수
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

BYTE __fastcall TFormMain::Hex2ByteConvert(AnsiString Value)
{
        // String Hex 값을 Byte 로 변환 함수
        int iResult1 = 0;
        int iResult2 = 0;
        AnsiString tempValue = Value.SubString(1, 1);
        if(tempValue.LowerCase() == "a")
            iResult1 = 10;
        else if(tempValue.LowerCase() == "b")
            iResult1 = 11;
        else if(tempValue.LowerCase() == "c")
            iResult1 = 12;
        else if(tempValue.LowerCase() == "d")
            iResult1 = 13;
        else if(tempValue.LowerCase() == "e")
            iResult1 = 14;
        else if(tempValue.LowerCase() == "f")
            iResult1 = 15;
        else
            iResult1 = StrToIntDef(tempValue, 0);
        if(Value.Length() > 1)
        {
            tempValue = Value.SubString(2, 1);
            if(tempValue.LowerCase() == "a")
                iResult2 = 10;
            else if(tempValue.LowerCase() == "b")
                iResult2 = 11;
            else if(tempValue.LowerCase() == "c")
                iResult2 = 12;
            else if(tempValue.LowerCase() == "d")
                iResult2 = 13;
            else if(tempValue.LowerCase() == "e")
                iResult2 = 14;
            else if(tempValue.LowerCase() == "f")
                iResult2 = 15;
            else
                iResult2 = StrToIntDef(tempValue, 0);
        }
        union{
            struct BIT {
                unsigned b0 : 4;
                unsigned b1 : 4;
            } bit;
            BYTE cData;
        }uData;
        uData.bit.b0 = iResult1;
        uData.bit.b1 = iResult2;
        return (int)uData.cData;
}
//---------------------------------------------------------------------------

AnsiString __fastcall TFormMain::IntTo485Address(int Value)
{
        // Byte 값을 로 String Hex 변환 함수
        AnsiString asResult = "";
        if(Value ==  10)
            asResult = "A";
        else if(Value ==  11)
            asResult = "B";
        else if(Value ==  12)
            asResult = "C";
        else if(Value ==  13)
            asResult = "D";
        else if(Value ==  14)
            asResult = "E";
        else if(Value ==  15)
            asResult = "F";
        else if(Value ==  16)
            asResult = "G";
        else if(Value ==  17)
            asResult = "H";
        else if(Value ==  18)
            asResult = "I";
        else if(Value ==  19)
            asResult = "J";
        else if(Value ==  20)
            asResult = "K";
        else if(Value ==  21)
            asResult = "L";
        else if(Value ==  22)
            asResult = "M";
        else if(Value ==  23)
            asResult = "N";
        else if(Value ==  24)
            asResult = "O";
        else if(Value ==  25)
            asResult = "P";
        else if(Value ==  26)
            asResult = "Q";
        else if(Value ==  27)
            asResult = "R";
        else if(Value ==  28)
            asResult = "S";
        else if(Value ==  29)
            asResult = "T";
        else if(Value ==  30)
            asResult = "U";
        else if(Value ==  31)
            asResult = "V";
        else
            asResult = IntToStr(Value);
        return asResult;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::AddLog(AnsiString Msg, TColor Color)
{
        // 로그 표시 및 저장 함수
        AnsiString Time = FormatDateTime("MM/DD hh:nn:ss", Now());
        try{
            if(RichEditLog->Lines->Count > 500)
                BtnLogClear->Click();
            RichEditLog->SelStart = RichEditLog->Text.Length();
            //if(Color == clBlack)
            //    RichEditLog->SelAttributes->Color = TextColor;
            //else
                RichEditLog->SelAttributes->Color = Color;
            RichEditLog->Lines->Add("[" + Time + "]\t " + Msg);
            SendMessage(RichEditLog->Handle, WM_VSCROLL, SB_BOTTOM, 0);
        }catch(...){
            BtnLogClear->Click();
        }
        AnsiString LogFileName = "";
        AnsiString Buff = "";
        LogFileName = BasePath + IncludeTrailingPathDelimiter(DestLogPath) + FormatDateTime("YYYYMMDD", Now()) + "_" + IntToStr(LogFileCnt) + ".log";
        Buff = "[" + Time + "]\t " + Msg + "\r\n";
        TFileStream * FS = NULL;
        try{
            if(FileExists(LogFileName))
                FS = new TFileStream(LogFileName, fmOpenReadWrite | fmShareCompat);
            else
            {
                DeleteOldLogFile(BasePath + IncludeTrailingPathDelimiter(DestLogPath));
                LogFileName = BasePath + IncludeTrailingPathDelimiter(DestLogPath) + FormatDateTime("YYYYMMDD", Now()) + "_" + IntToStr(LogFileCnt) + ".log";
                FS = new TFileStream(LogFileName, fmCreate | fmShareCompat);
            }
            FS->Position = FS->Size;
            FS->Write(Buff.c_str(), Buff.Length());
            int tempSize = FS->Size + Buff.Length();
            if(tempSize > (512 * 1024))
            {
                LogFileCnt++;
                SaveIniFile();
            }
            OldLogDate = FormatDateTime("YYYYMMDD", Now());
        }catch (...){
        }
        if(FS)
        {
            delete FS;
            FS = NULL;
        }
        if(FormLog->Showing)
        {
            try{
                FormLog->RichEditLog->Text = RichEditLog->Text;
                SendMessage(FormLog->RichEditLog->Handle, WM_VSCROLL, SB_BOTTOM, 0);
            }catch (...){
            }
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::DeleteOldLogFile(AnsiString SitePath)
{
        // 오래된 로그 파일 삭제 함수
        if(SitePath == " " || SitePath == "")
            return;
        AnsiString FileName = "";
        AnsiString path = SitePath + "*.*";//LogPath + "*.*";
        TSearchRec sr;

        TDateTime dtToday = Now();
        TDateTime dtFile;

        int iAttributes = faAnyFile;
        if(0 == FindFirst(path, iAttributes, sr))
        {
            do
            {
                if((sr.Attr & faDirectory) == faDirectory)
                    continue;
                else
                {
                    dtFile = FileDateToDateTime(sr.Time);
                    if((dtToday - 7) >= dtFile)
                    {
                        FileName = BasePath + DestLogPath + "\\" + sr.Name;
                        if(ExtractFileExt(FileName).AnsiCompareIC(".log") == 0)
                            DeleteFile(FileName);
                    }
                }
            } while(0 == FindNext(sr));
        }
        FindClose(sr);
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BitBtnExitClick(TObject *Sender)
{
        // 프로그램 종료   
        //ProtocolTabKind = pcMessage->ActivePageIndex;
        SaveIniFile();
        Application->Terminate();
}
//---------------------------------------------------------------------------

bool __fastcall TFormMain::CurrenTimeSync()
{
        // PC 현재시간 전송
        bStopSend = false;
        bool Connect = false;
        int Addr = 0;
	if(bRS485)
            Addr = Controler1;
        AnsiString CurTimeData;
        TDateTime CurTime;
        CurTime = Now();
        BYTE TimeData[21];
        int iSendSize = 7;
        ZeroMemory(TimeData, sizeof(TimeData));
        TimeData[0] = StrToInt("0x" + FormatDateTime("yy", CurTime).TrimRight());
        TimeData[1] = StrToInt("0x" + FormatDateTime("mm", CurTime).TrimRight());
        TimeData[2] = StrToInt("0x" + FormatDateTime("dd", CurTime).TrimRight());
        TimeData[3] = StrToInt("0x" + AnsiString(ReturnDay(CurTime)).TrimRight());
        TimeData[4] = StrToInt("0x" + FormatDateTime("hh", CurTime).TrimRight());
        TimeData[5] = StrToInt("0x" + FormatDateTime("nn", CurTime).TrimRight());
        TimeData[6] = StrToInt("0x" + FormatDateTime("ss", Now()).TrimRight());
        if(ComPortSet(ComKind, PortUse1, BaudRateUse1, Disable, Disable, IPAddress, IPPort, false))
        {
            if(PorotocolType == 1)
            {
                AnsiString asSendData = "![" + IntTo485Address(Addr) + "030";
                //char Buf[3];
                asSendData = asSendData + FormatDateTime("yy", CurTime).TrimRight();
                asSendData = asSendData + FormatDateTime("mm", CurTime).TrimRight();
                asSendData = asSendData + FormatDateTime("dd", CurTime).TrimRight();
                //ZeroMemory(Buf, sizeof(Buf));
                //wsprintf(Buf, "%01d", AnsiString(ReturnDay(CurTime)).TrimRight());
                asSendData = asSendData + AnsiString(ReturnDay(CurTime)).TrimRight();
                asSendData = asSendData + FormatDateTime("hh", CurTime).TrimRight();
                asSendData = asSendData + FormatDateTime("nn", CurTime).TrimRight();
                asSendData = asSendData + FormatDateTime("ss", Now()).TrimRight() + "!]";
                iSendSize = asSendData.Length();
                memcpy(TimeData, asSendData.c_str(), iSendSize);
                if(ModuleASCIIData(ComKind, Addr, TimeData, iSendSize, bMsg))
                {
                    if(bDataFail)
                        AddLog(LangFile.BtnSyncLED + " [FAIL]" + asLogMessage, clRed);
                    else
                        AddLog(LangFile.BtnSyncLED + " [OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(LangFile.BtnSyncLED + " [FAIL]" + asLogMessage, clRed);
            }
            else
            {
                Connect = ModuleData(ComKind, Addr, TimeData, iSendSize, 0, CT, bMsg);
                if(Connect)
                {
                    if(bDataFail)
                        AddLog(LangFile.BtnSyncLED + " [FAIL]" + asLogMessage, clRed);
                    else
                        AddLog(LangFile.BtnSyncLED + " [OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(LangFile.BtnSyncLED + " [FAIL]" + asLogMessage, clRed);
            }
            ComClose(ComKind);
        }
        return Connect;
}
//---------------------------------------------------------------------------

int __fastcall TFormMain::ReturnDay(TDateTime Time)
{
        // 요일 리턴 값 함수
        int iDay = DayOfWeek(Time);
        switch(iDay)
        {
            case 1:
                iDay = 7;
                break;
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
                iDay -= 1;
                break;
            default:
                iDay = 1;
                break;
        }
        return iDay;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::ComPort1RxChar(TObject *Sender, int Count)
{
        // 시리얼 통신 데이터 받는 이벤트
        int Size = Count;
        char *Buffer = new char[Size];
        ZeroMemory(Buffer, Size);
        ComPort1->Read(Buffer, Size);
        ReadParsing(0, 0, Buffer, Size);
        if(Buffer)
        {
            delete [] Buffer;
            Buffer = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::IdUDPClientStatus(TObject *ASender,
      const TIdStatus AStatus, const AnsiString AStatusText)
{
        // UDP 클라이언트 연결 상태 이벤트
        if(AStatus == hsConnected)
            DNSConnect = true;
        else if(AStatus == hsDisconnected)
            bDisConnect = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::IdUDPServerStatus(TObject *ASender,
      const TIdStatus AStatus, const AnsiString AStatusText)
{
        // UDP 서버 연결 상태 이벤트
        if(AStatus == hsConnected)
            DNSConnect = true;
        else if(AStatus == hsDisconnected)
            bDisConnect = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::IdUDPServerUDPRead(TObject *Sender,
      TStream *AData, TIdSocketHandle *ABinding)
{
        // UDP 데이터 받는 이벤트
        if(!ABinding->HandleAllocated)
            return;
        if(!AData->Size && AData->Size > 2048)
            return;
        int Size = AData->Size;
        char *Buffer = new char[Size];
        ZeroMemory(Buffer, Size);
        AData->ReadBuffer(Buffer, Size);
        ReadParsing(3, 0, Buffer, Size);
        if(Buffer)
        {
            delete [] Buffer;
            Buffer = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::ServerSocket1ClientConnect(TObject *Sender,
      TCustomWinSocket *Socket)
{
        try{
            ServerClientSocket = Socket;
        }catch(...){
        }
        DNSConnect = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::ServerSocket1ClientDisconnect(TObject *Sender,
      TCustomWinSocket *Socket)
{
        bDisConnect = true;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::ServerSocket1ClientError(TObject *Sender,
      TCustomWinSocket *Socket, TErrorEvent ErrorEvent, int &ErrorCode)
{
        ErrorCode = 0;
        switch (ErrorEvent)
        {
            case eeGeneral, eeSend, eeReceive, eeConnect, eeDisconnect, eeAccept, eeLookup:
            break;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::ServerSocket1ClientRead(TObject *Sender,
      TCustomWinSocket *Socket)
{
        int Size = Socket->ReceiveLength();
        char *Buffer = new char[Size];
        ZeroMemory(Buffer, Size);
        Socket->ReceiveBuf(Buffer, Size);
        ReadParsing(2, 0, Buffer, Size);
        if(Buffer)
        {
            delete [] Buffer;
            Buffer = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::ReadParsing(BYTE ComKind, char Add, BYTE *ParsingData, int ParsingSize)
{
        // 받은 데이터 파싱 함수
        BYTE ch;
        for(int i = 0; i < ParsingSize; i++)
        {
            ch = ParsingData[i];
            switch(RP.CS)
            {
                case SynSDLEHead :
                {
                    if(RP.Status == 1 && PorotocolType == 1)
                    {
                        RP.Rcnt++;
                        if(RP.Rcnt > 24)
                        {
                            RP.Status = 0;
                            RP.Rcnt = 0;
                        }
                        /*if(ch == 'T' && ParsingData[i + 1] == 'X' && ParsingData[i + 2] == '(')
                        {
                           RP.Data[RP.Rcnt++] = ch;
                           RP.CS = SynASCIISTX;
                        }*/
                    }
                    else if(ch == DLE)
                        RP.CS = SynSTXHead;
                    else if(ch == 'D')
                    {
                        RP.Data[RP.Rcnt++] = ch;
                        RP.CS = SynBlock;
                    }
                    else if(ch == '!')
                        RP.CS = SynASCIISTX;
                    else if(ch == 'R' && PorotocolType == 1)
                    {
                        RP.Data[RP.Rcnt++] = ch;
                        RP.CS = SynASCIISTX;
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
                    if(ch == PC || ch == CT || ch == RCT  || ch == BS || ch == FMMC || ch == EDI || ch == FMI || ch == FI || ch == SC ||
                        ch == SO || ch == FED || ch == FND || ch == FRW || ch == DTS || ch == ED || ch == DC || ch == FFNT || ch == FDPI || ch == FU ||
                        ch == DS || ch == DFS || ch == BC || ch == DSI || ch == DSC || ch == DCT)
                    {
                        RP.OPCode = ch;
                        RP.CS = SynDataField;
                    }
                    else
                        ZeroMemory(&RP, sizeof(RP));
                    break;
                }
                case SynDataField :
                {
                    int dl = 0;
                    int rdl = 0;
                    bool btempBCC = false;
                    //if(ComKind == 0)
                    //    btempBCC = true;
                    //else
                    //    btempBCC = false;
                    rdl = 256 * 256;
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
                    ch = ParsingData[i];
                    RP.Data[RP.Rcnt++] = ch;
                    if(RP.Rcnt >= rdl)
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
                    int dl = 0;
                    bool btempBCC = false;
                    if(RP.OPCode == FU)
                        btempBCC = true;
                    else
                        btempBCC = FlagCrcPacket;
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
                        dl = (int)uSize.Bit.bit0 * 256 + (int)RP.LLen;
                        if(btempBCC)
                            dl -= 2;
                        SchBmpFile = true;
                        bDataFail = false;
                        switch(RP.OPCode)
                        {
                            case PC :
                            case BC :
                            case CT :
                            case FMMC :
                            case EDI :
                            case FMI :
                            case SO :
                            case FED :
                            case FND :
                            case FFNT :
                            case ED :
                            case DC :
                            case DTS :
                            case DS :
                            case DFS :
                            case SC :
                            case DCT :
                            {
                                if((RP.Data[0] >= 0x04) && (RP.Rcnt < 2))
                                {
                                    bDataFail = true;
                                    FailData = RP.Data[0];
                                    //AddLog("", "[DATA FAIL]", clRed);
                                }
                                break;
                            }
                            case FRW :
                            {
                                AnsiString tempData = "";
                                int iPos = 0;
                                if(RP.Rcnt > 2)
                                {
                                    for(int j = 0; j < 2; j++)
                                    {
                                        for(int i = 0; i < 3; i++)
                                        {
                                            FontFileNameVersion[j][i] = AnsiString((char*)&RP.Data[1 + iPos * 36]).SubString(1, 36);
                                            iPos++;
                                        }
                                    }
                                }
                                if((RP.Data[0] >= 0x04) && (RP.Rcnt < 2))
                                {
                                    bDataFail = true;
                                    FailData = RP.Data[0];
                                    //AddLog("", "[DATA FAIL]", clRed);
                                }
                                break;
                            }
                            case BS :
                            {
                                if(RP.Data[0] == 0xF1)
                                {
                                    if(ModuleHeight > RP.Data[1] || ModuleWidth > RP.Data[2])
                                    {
                                        if(ModuleHeight > RP.Data[1])
                                        {
                                            ModuleHeight = RP.Data[1];
                                            //UpDownHeight->Position = ModuleHeight;
                                        }
                                        if(ModuleWidth > RP.Data[2])
                                        {
                                            ModuleWidth = RP.Data[2];
                                            //UpDownWidth->Position = ModuleWidth;
                                        }
                                        AnsiString asMessage1 = "The screen setting exceeds the maximum screen size of the controller.";
                                        AnsiString asMessage2 = "So, it is set to the maximum size of the controller.";
                                        Message("\r\n" + asMessage1 + "\r\n\r\n" + asMessage2 + "\r\n", LangFile.MessageButton04, "", "", asMessage1.Length() - 2, 5, 1, clRed);
                                        UpDownHeight->Position = ModuleHeight;
                                        UpDownWidth->Position = ModuleWidth;
                                    }
                                }
                                if((RP.Data[0] >= 0x04) && (RP.Rcnt < 2))
                                {
                                    bDataFail = true;
                                    FailData = RP.Data[0];
                                    //AddLog("", "[DATA FAIL]", clRed);
                                }
                                break;
                            }
                            case DSC :
                            {
                                if(FLEDSet && RP.Rcnt >= 2)
                                {
                                    FLEDSet->UpDownDetailDelayBefore->Position = RP.Data[0];
                                    FLEDSet->UpDownDetailDelayAfter->Position = RP.Data[1];
                                }
                                if((RP.Data[0] >= 0x04) && (RP.Rcnt < 2))
                                {
                                    bDataFail = true;
                                    FailData = RP.Data[0];
                                    //AddLog("", "[DATA FAIL]", clRed);
                                }
                                break;
                            }
                            case FU :
                            {
                                if(RP.Data[0] == 0x01)
                                {
                                    bStopSend = true;
                                    Message("\r\n" + LangFile.Message16 + "\r\n", LangFile.MessageButton04, "", "", LangFile.Message16.Length() - 2, 3, 1, clBlack);
                                }
                                else if(RP.Data[0] == 0x80)
                                {
                                    bStopSend = true;
                                    Message("\r\n" + LangFile.Message16 + "\r\n", LangFile.MessageButton04, "", "", LangFile.Message16.Length() - 2, 3, 1, clBlack);
                                }
                                if((RP.Data[0] >= 0x04))// && (RP.Rcnt > 1))
                                {
                                    bDataFail = true;
                                    FailData = RP.Data[0];
                                    //AddLog("", "[DATA FAIL]", clRed);
                                }
                            }
                            break;
                            case FI :
                            {
                                BYTE ConectFlag = RP.Data[0];
                                AnsiString asData = "";
                                //memcpy(&RP.Data[1], "[DABIT260P]-02x012-007    V05.30 2012-12-03 DABIT260P_3C_4R12C         ", 71);
                                if(RP.Rcnt < 202)
                                    asData = AnsiString((char*)&RP.Data[1]).SubString(1, dl - 1);
                                else
                                    asData = AnsiString((char*)&RP.Data[1]).SubString(1, 201);
                                int asPos = 0;
                                asPos = asData.Pos("_") + 6;
                                AnsiString VerInfo1 = asData.SubString(1, asPos).TrimRight();
                                AnsiString VerInfo2 = asData.SubString(asPos + 1, asData.Length() - asPos).TrimRight();
                                asPos = VerInfo2.Pos("\r");
                                VerInfo2 = VerInfo2.Delete(asPos, 1);
                                VerInfo2 = VerInfo2.Insert(" ", asPos);
                                asPos = VerInfo2.Pos(" V") + 2;
                                float fVer = StrToFloatDef(VerInfo2.SubString(asPos, 5), 6.5);
                                if(FFirmware)
                                    FFirmware->Label1->Caption = asData;
                                if((RP.Data[0] >= 0x04) && (RP.Rcnt < 2))
                                {
                                    bDataFail = true;
                                    FailData = RP.Data[0];
                                    //AddLog("", "[DATA FAIL]", clRed);
                                }
                                break;
                            }
                            case FDPI :
                            {
                                ProgramType = RP.Data[1];
                                char Buf[3];
                                ZeroMemory(Buf, 3);
                                sprintf(Buf, "%02x", RP.Data[2]);
                                char Buf2[3];
                                ZeroMemory(Buf2, 3);
                                sprintf(Buf2, "%02x", RP.Data[3]);
                                AnsiString asData = AnsiString(Buf) + "." + AnsiString(Buf2);
                                float fVer = StrToFloat(asData);
                                //AnsiString Data = AnsiString(RP.Data[2]);
                                //fVer = StrToIntDef("0x" + Setup.btTime1.TrimRight()
                                //bNewPacket = true;
                                //if(RP.Data[4] == (BYTE)char('C'))
                                //    Setup.ImageComp = true;
                                //else
                                //    Setup.ImageComp = false;
                                //if(RP.Data[4] > 0)
                                //    UseSD = true;
                                //else
                                //    UseSD = false;
                                //if(RP.Data[5] < bBlockCount)
                                    ImageSendingBlockCount = RP.Data[5];
                                if((RP.Data[0] >= 0x04) && (RP.Rcnt < 2))
                                {
                                    bDataFail = true;
                                    FailData = RP.Data[0];
                                    //AddLog("", "[DATA FAIL]", clRed);
                                }
                                break;
                            }
                            case RCT :
                            {
                                union{
                                    BYTE cbSize;
                                    struct{
                                        unsigned bit0 : 4;
                                        unsigned bit1 : 4;
                                    }Bit;
                                }uSize;
                                AnsiString asDIBDTime = "";
                                char cNum = 0x00;
                                uSize.cbSize = RP.Data[0];
                                cNum = uSize.Bit.bit1 + 0x30;
                                asDIBDTime += cNum;
                                cNum = uSize.Bit.bit0 + 0x30;
                                asDIBDTime = asDIBDTime + cNum + "-";
                                uSize.cbSize = RP.Data[1];
                                cNum = uSize.Bit.bit1 + 0x30;
                                asDIBDTime += cNum;
                                cNum = uSize.Bit.bit0 + 0x30;
                                asDIBDTime = asDIBDTime + cNum + "-";
                                uSize.cbSize = RP.Data[2];
                                cNum = uSize.Bit.bit1 + 0x30;
                                asDIBDTime += cNum;
                                cNum = uSize.Bit.bit0 + 0x30;
                                asDIBDTime = asDIBDTime + cNum + " (" + GetDay((int)RP.Data[3]) + ") ";
                                uSize.cbSize = RP.Data[4];
                                cNum = uSize.Bit.bit1 + 0x30;
                                asDIBDTime += cNum;
                                cNum = uSize.Bit.bit0 + 0x30;
                                asDIBDTime = asDIBDTime + cNum + ":";
                                uSize.cbSize = RP.Data[5];
                                cNum = uSize.Bit.bit1 + 0x30;
                                asDIBDTime += cNum;
                                cNum = uSize.Bit.bit0 + 0x30;
                                asDIBDTime = asDIBDTime + cNum + ":";
                                uSize.cbSize = RP.Data[6];
                                cNum = uSize.Bit.bit1 + 0x30;
                                asDIBDTime += cNum;
                                cNum = uSize.Bit.bit0 + 0x30;
                                asDIBDTime += cNum;
                                Label25->Caption = asDIBDTime;
                                if(FETC)
                                    FETC->Label25->Caption = asDIBDTime;
                                if((RP.Data[0] >= 0x04) && (RP.Rcnt < 2))
                                {
                                    bDataFail = true;
                                    FailData = RP.Data[0];
                                    //AddLog("", "[DATA FAIL]", clRed);
                                }
                                break;
                            }
                        }
                        BYTE bTempData[1];
                        bTempData[0] = 0x00;
                        asLogMessage = asLogMessage + "\r\n                \t Receive : 10 02 " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.HLen) + " "
                             + Byte2HexConvert(RP.LLen) + " " + Byte2HexConvert(RP.OPCode) + " ";
                        if(dl - 1 < 25)
                        {
                            for(int j = 0; j < dl - 1; j++)
                                asLogMessage = asLogMessage + Byte2HexConvert(RP.Data[j]) + " ";
                        }
                        else
                        {
                            for(int i = 0; i < 30; i++)
                                asLogMessage = asLogMessage + Byte2HexConvert(RP.Data[i]) + " ";
                            asLogMessage = asLogMessage + "~ ";
                            if(btempBCC)
                            {
                                for(int i = dl - 1 - 4; i < dl - 1 - 2; i++)
                                    asLogMessage = asLogMessage + Byte2HexConvert(RP.Data[i]) + " ";
                                for(int j = 0; j < sizeof(RP.Bcc); j++)
                                    asLogMessage = asLogMessage + Byte2HexConvert(RP.Bcc[j]) + " ";
                            }
                            else
                            {
                                for(int i = dl - 1 - 2; i < dl - 1; i++)
                                    asLogMessage = asLogMessage + Byte2HexConvert(RP.Data[i]) + " ";
                            }
                        }
                        asLogMessage += "10 03 ";
                    }
                    ZeroMemory(&RP, sizeof(RP));
                    break;
                }
                case SynASCIISTX :
                {
                    if(ch == '[')
                        RP.CS = SynASCIIAddr;
                    else if(ch == 'X' && ParsingData[i + 1] == '(')
                    {
                        RP.Status = 1;
                        RP.Rcnt = 0;
                        //if(RP.Data[0] == 'T')
                        //    RP.Status = 0;
                        RP.CS = SynSDLEHead;
                    }
                    else
                        ZeroMemory(&RP, sizeof(RP));
                    break;
                }
                case SynASCIIAddr :
                {
                    RP.Add = ch;
                    RP.CS = SynASCIICheck;
                    break;
                }
                case SynASCIICheck :
                {
                    RP.SendID = ch;
                    RP.CS = SynASCIICOMMCode;
                    break;
                }
                case SynASCIICOMMCode :
                {
                    if(RP.Rcnt == 0)
                        RP.OPCode = ch;
                    RP.Data[RP.Rcnt++] = ch;
                    if(RP.OPCode <= 0x31)
                    {
                        RP.Rcnt = 0;
                        RP.CS = SynASCIIStatus;
                    }
                    else if(RP.OPCode == 0x33)
                    {
                        if(RP.Data[1] == 0x33)
                        {
                            if(ch == '!')
                            {
                                RP.Status = RP.Data[2];
                                //RP.Rcnt = 0;
                                RP.CS = SynASCIIETX;
                                continue;
                            }
                            if(RP.Rcnt >= 58)
                            {
                                //RP.Rcnt = 0;
                                RP.CS = SynASCIIETXHead;
                            }
                        }
                        else if(RP.Data[1] == 0x31)
                        {
                            if(ch == '!')
                            {
                                RP.Status = RP.Data[2];
                                //RP.Rcnt = 0;
                                RP.CS = SynASCIIETX;
                                continue;
                            }
                            if(RP.Rcnt >= 15)
                            {
                                //RP.Rcnt = 0;
                                RP.CS = SynASCIIETXHead;
                            }
                        }
                        else
                        {
                            if(RP.Rcnt >= 2)
                            {
                                //RP.Rcnt = 0;
                                RP.CS = SynASCIIStatus;
                            }
                        }
                    }
                    else if(RP.OPCode == 0x34)
                    {
                        if(ch == '!')
                        {
                            RP.Status = RP.Data[2];
                            //RP.Rcnt = 0;
                            RP.CS = SynASCIIETX;
                            continue;
                        }
                        if(RP.Rcnt >= 7)
                        {
                            //RP.Rcnt = 0;
                            RP.CS = SynASCIIETXHead;
                        }
                    }
                    else if(RP.OPCode == 0x38)
                    {
                        if(RP.Data[1] == 0x31)
                        {
                            if(ch == '!')
                            {
                                RP.Status = RP.Data[2];
                                //RP.Rcnt = 0;
                                RP.CS = SynASCIIETX;
                                continue;
                            }
                        }
                        else
                        {
                            if(RP.Rcnt >= 175)
                            {
                                //RP.Rcnt = 0;
                                RP.CS = SynASCIIETXHead;
                            }
                        }
                    }
                    else if(RP.OPCode == 0x39)
                    {
                        if(RP.Data[1] == 0x35 || RP.Data[1] == 0x36)
                        {
                            if(ch == '!')
                            {
                                RP.Status = RP.Data[2];
                                //RP.Rcnt = 0;
                                RP.CS = SynASCIIETX;
                                continue;
                            }
                        }
                        else
                        {
                            if(RP.Rcnt >= 219)
                            {
                                //RP.Rcnt = 0;
                                RP.CS = SynASCIIETXHead;
                            }
                        }
                    }
                    else if(RP.OPCode == 'B')
                    {
                        if(ch == '!')
                        {
                            RP.Status = RP.Data[2];
                            //RP.Rcnt = 0;
                            RP.CS = SynASCIIETX;
                            continue;
                        }
                        if(RP.Rcnt >= 18)
                        {
                            //RP.Rcnt = 0;
                            RP.CS = SynASCIIETXHead;
                        }
                    }
                    else if(RP.OPCode == 'D')
                    {
                        if(ch == '!')
                        {
                            RP.Status = RP.Data[2];
                            //RP.Rcnt = 0;
                            RP.CS = SynASCIIETX;
                            continue;
                        }
                        if(RP.Rcnt >= 20)
                        {
                            //RP.Rcnt = 0;
                            RP.CS = SynASCIIETXHead;
                        }
                    }
                    else
                    {
                        if(ch == '!')
                        {
                            RP.Status = RP.Data[2];
                            //RP.Rcnt = 0;
                            RP.CS = SynASCIIETX;
                            continue;
                        }
                        if(RP.Rcnt >= 175)
                        {
                            //RP.Rcnt = 0;
                            RP.CS = SynASCIIETXHead;
                        }
                    }
                    break;
                }
                case SynASCIIStatus :
                {
                    RP.Status = ch;
                    RP.CS = SynASCIIETXHead;
                    break;
                }
                case SynASCIIETXHead :
                {
                    if(ch == '!')
                        RP.CS = SynASCIIETX;
                    else
                        ZeroMemory(&RP, sizeof(RP));
                    break;
                }
                case SynASCIIETX :
                {
                    if(ch == ']')
                    {
                        SchBmpFile = true;
                        bDataFail = false;
                        if(RP.Status == 'F')
                            bDataFail = true;
                        if(RP.SendID == '0')
                        {
                            try{
                                //if(asJohap != "" && asJohap != " " && PorotocolType == 1)
                                //    asLogMessage = asJohap;
                                if(RP.OPCode <= 0x31)
                                {
                                    if(PorotocolType == 1)
                                    {
                                        asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                             + AnsiString((char)RP.OPCode) + AnsiString((char)RP.Status) + "!]";
                                    }
                                    else
                                    {
                                        asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                             + Byte2HexConvert(RP.OPCode) + " " + Byte2HexConvert(RP.Status) + " 21 5D";
                                    }
                                }
                                else if(RP.OPCode == 0x33)
                                {
                                    if(RP.Data[1] == 0x33)
                                    {
                                        if(PorotocolType == 1)
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                 + AnsiString((char)RP.OPCode);
                                        }
                                        else
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                                 + Byte2HexConvert(RP.OPCode) + " ";
                                        }
                                        if(RP.Status == 'F')
                                        {
                                            if(PorotocolType == 1)
                                            {
                                                asLogMessage = asLogMessage + AnsiString((char)RP.Data[1]) + AnsiString((char)RP.Status) + "!]";
                                            }
                                            else
                                            {
                                                asLogMessage = asLogMessage + Byte2HexConvert(RP.OPCode) + " " + Byte2HexConvert(RP.Data[1]) + " " + Byte2HexConvert(RP.Status) + " 21 5D";
                                            }
                                        }
                                        else
                                        {
                                            if(PorotocolType == 1)
                                            {
                                                asLogMessage = asLogMessage + AnsiString((char*)&RP.Data[1]).SubString(1, 57) + "!]";
                                            }
                                            else
                                            {
                                                for(int j = 1; j <= 57; j++)
                                                    asLogMessage = asLogMessage + Byte2HexConvert(RP.Data[j]) + " ";
                                                asLogMessage = asLogMessage + "21 5D";
                                            }
                                            if(FASCIISet)
                                                FASCIISet->mASCIIText11->Text = "![0032" + AnsiString((char *)&RP.Data[2]).SubString(1, 56) + "!]";
                                        }
                                    }
                                    else if(RP.Data[1] == 0x31)
                                    {
                                        if(PorotocolType == 1)
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                 + AnsiString((char)RP.OPCode) + AnsiString((char*)&RP.Data[1]).SubString(1, 15) + "!]";
                                        }
                                        else
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                                 + Byte2HexConvert(RP.OPCode) + " ";
                                            for(int j = 1; j <= 15; j++)
                                                asLogMessage = asLogMessage + Byte2HexConvert(RP.Data[j]) + " ";
                                            asLogMessage = asLogMessage + "21 5D";
                                        }
                                        Label25->Caption = AnsiString((char*)&RP.Data[2]).SubString(1, 2) + "-" + AnsiString((char*)&RP.Data[2]).SubString(3, 2) + "-" + AnsiString((char*)&RP.Data[2]).SubString(5, 2) +
                                            " (" + GetDay(StrToIntDef((char)RP.Data[8], 0)) + ") " + AnsiString((char*)&RP.Data[2]).SubString(8, 2) + ":" + AnsiString((char*)&RP.Data[2]).SubString(10, 2) + ":" + AnsiString((char*)&RP.Data[2]).SubString(12, 2);
                                        if(FETC)
                                            FETC->Label25->Caption = Label25->Caption;
                                    }
                                    else
                                    {
                                        if(PorotocolType == 1)
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                 + AnsiString((char)RP.OPCode) + AnsiString((char)RP.Data[1]) + AnsiString((char)RP.Status) + "!]";
                                        }
                                        else
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                             + Byte2HexConvert(RP.OPCode) + " " + Byte2HexConvert(RP.Data[1]) + " " + Byte2HexConvert(RP.Status) + " 21 5D";
                                        }
                                    }
                                }
                                else if(RP.OPCode == 0x34)
                                {
                                    if(RP.Status == 'F')
                                    {
                                        if(PorotocolType == 1)
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                 + AnsiString((char)RP.OPCode) + AnsiString((char)RP.Status) + "!]";
                                        }
                                        else
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                                 + Byte2HexConvert(RP.OPCode) + " " + Byte2HexConvert(RP.Status) + " 21 5D";
                                        }
                                    }
                                    else
                                    {
                                        AnsiString asData = "";
                                        if(RP.Rcnt < 7)
                                            asData = AnsiString((char*)&RP.Data[1]).SubString(1, RP.Rcnt - 2);
                                        else
                                            asData = AnsiString((char*)&RP.Data[1]).SubString(1, 7);
                                        if(PorotocolType == 1)
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                 + AnsiString((char)RP.OPCode) + AnsiString((char*)&RP.Data[1]).SubString(1, 7) + "!]";
                                        }
                                        else
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                                 + Byte2HexConvert(RP.OPCode) + " ";
                                            for(int j = 1; j <= 7; j++)
                                                asLogMessage = asLogMessage + Byte2HexConvert(RP.Data[j]) + " ";
                                            asLogMessage = asLogMessage + "21 5D";
                                        }
                                        AnsiString tempData = asData.SubString(2, 2);
                                        int tempHeight = StrToIntDef(tempData, 2);
                                        int tempWidth = 0;
                                        if(tempData != "" && tempHeight != 0)
                                        {
                                            tempData = asData.SubString(4, 2);
                                            tempWidth = StrToIntDef(tempData, 15);
                                            if(tempData != "" && tempWidth != 0)
                                            {
                                                if(ModuleHeight > tempHeight || ModuleWidth > tempWidth)
                                                {
                                                    if(ModuleHeight > tempHeight)
                                                    {
                                                        ModuleHeight = tempHeight;
                                                        //UpDownHeight->Position = ModuleHeight;
                                                    }
                                                    if(ModuleWidth > tempWidth)
                                                    {
                                                        ModuleWidth = tempWidth;
                                                        //UpDownWidth->Position = ModuleWidth;
                                                    }
                                                    AnsiString asMessage1 = "The screen setting exceeds the maximum screen size of the controller.";
                                                    AnsiString asMessage2 = "So, it is set to the maximum size of the controller.";
                                                    Message("\r\n" + asMessage1 + "\r\n\r\n" + asMessage2 + "\r\n", LangFile.MessageButton04, "", "", asMessage1.Length() - 2, 5, 1, clRed);
                                                    UpDownHeight->Position = ModuleHeight;
                                                    UpDownWidth->Position = ModuleWidth;
                                                }
                                            }
                                        }
                                    }
                                }
                                else if(RP.OPCode == 0x38)
                                {
                                    if(RP.Status == 'F')
                                    {
                                        if(PorotocolType == 1)
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                 + AnsiString((char)RP.OPCode) + AnsiString((char)RP.Status) + "!]";
                                        }
                                        else
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                                 + Byte2HexConvert(RP.OPCode) + " " + Byte2HexConvert(RP.Status) + " 21 5D";
                                        }
                                    }
                                    else
                                    {
                                        AnsiString asData = "";
                                        if(RP.Rcnt <= 156)
                                            asData = AnsiString((char*)&RP.Data[2]).SubString(1, RP.Rcnt - 3);
                                        else
                                            asData = AnsiString((char*)&RP.Data[2]).SubString(1, 176);
                                        int asPos = 0;
                                        asPos = asData.Pos("_") + 6;
                                        AnsiString VerInfo1 = asData.SubString(1, asPos).TrimRight();
                                        AnsiString VerInfo2 = asData.SubString(asPos + 1, asData.Length() - asPos).TrimRight();
                                        asPos = VerInfo2.Pos("\r");
                                        VerInfo2 = VerInfo2.Delete(asPos, 1);
                                        VerInfo2 = VerInfo2.Insert(" ", asPos);
                                        asPos = VerInfo2.Pos(" V") + 2;
                                        float fVer = StrToFloatDef(VerInfo2.SubString(asPos, 5), 6.5);
                                        if(FFirmware)
                                            FFirmware->Label1->Caption = asData;
                                        if(PorotocolType == 1)
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                 + AnsiString((char)RP.OPCode) + asData + "!]";
                                        }
                                        else
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                             + Byte2HexConvert(RP.OPCode) + " ";
                                            for(int j = 1; j <= 176; j++)
                                                asLogMessage = asLogMessage + Byte2HexConvert(RP.Data[j]) + " ";
                                            asLogMessage = asLogMessage + "21 5D";
                                        }
                                        /*AnsiString tempData = asData.SubString(20, 2);
                                        int tempHeight = StrToIntDef(tempData, 2);
                                        tempData = asData.SubString(23, 3);
                                        int tempWidth = StrToIntDef(tempData, 15);
                                        if(ModuleHeight > tempHeight || ModuleWidth > tempWidth)
                                        {
                                            if(ModuleHeight > tempHeight)
                                            {
                                                ModuleHeight = tempHeight;
                                                //UpDownHeight->Position = ModuleHeight;
                                            }
                                            if(ModuleWidth > tempWidth)
                                            {
                                                ModuleWidth = tempWidth;
                                                //UpDownWidth->Position = ModuleWidth;
                                            }
                                            AnsiString asMessage1 = "The screen setting exceeds the maximum screen size of the controller.";
                                            AnsiString asMessage2 = "So, it is set to the maximum size of the controller.";
                                            Message("\r\n" + asMessage1 + "\r\n\r\n" + asMessage2 + "\r\n", LangFile.MessageButton04, "", "", asMessage1.Length() - 2, 5, 1, clRed);
                                        }*/
                                    }
                                }
                                else if(RP.OPCode == 0x39)
                                {
                                    if(RP.Status == 'F')
                                    {
                                        if(PorotocolType == 1)
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                 + AnsiString((char)RP.OPCode) + AnsiString((char)RP.Status) + "!]";
                                        }
                                        else
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                                 + Byte2HexConvert(RP.OPCode) + " " + Byte2HexConvert(RP.Status) + " 21 5D";
                                        }
                                    }
                                    else
                                    {
                                        if(RP.Data[1] == 0x35 || RP.Data[1] == 0x36)
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                 + AnsiString((char)RP.OPCode);
                                            if(FFont && RP.Rcnt > 4)
                                            {
                                                AnsiString tempData = AnsiString((char*)RP.Data).SubString(3, 2);
                                                int iPos = 0;
                                                int iTextLen = 0;
                                                for(int j = 0; j < 2; j++)
                                                {
                                                    for(int i = 0; i < 3; i++)
                                                    {
                                                        FontFileNameVersion[j][i] = AnsiString((char*)&RP.Data[4 + iPos * 36]).SubString(1, 36);
                                                        asLogMessage += FontFileNameVersion[j][i];
                                                        iTextLen = FontFileNameVersion[j][i].Length();
                                                        if(iTextLen < 36)
                                                        {
                                                            for(int k = 0; k < 36 - iTextLen; k++)
                                                                asLogMessage += " ";
                                                        }
                                                        iPos++;
                                                    }
                                                }
                                                asLogMessage += "!]";
                                            }
                                            else
                                                asLogMessage += AnsiString((char)RP.OPCode) + AnsiString((char*)&RP.Data[1]).SubString(1, RP.Rcnt) + "!]";
                                        }
                                        else
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                 + AnsiString((char)RP.OPCode) + AnsiString((char*)&RP.Data[1]).SubString(1, RP.Rcnt) + "!]";
                                        }
                                    }
                                }
                                else if(RP.OPCode == 'B')
                                {
                                    if(RP.Status == 'F')
                                    {
                                        if(PorotocolType == 1)
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                 + AnsiString((char)RP.OPCode) + AnsiString((char)RP.Status) + "!]";
                                        }
                                        else
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                                 + Byte2HexConvert(RP.OPCode) + " " + Byte2HexConvert(RP.Status) + " 21 5D";
                                        }
                                    }
                                    else
                                    {
                                        if(RP.Data[1] == 0x31)
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                + AnsiString((char)RP.OPCode) + AnsiString((char*)&RP.Data[1]).SubString(1, 11) + "!]";
                                        }
                                        else if(RP.Data[1] == 0x34 || RP.Data[1] == 0x35)
                                        {
                                            if(FLEDSet && RP.Rcnt > 4)
                                            {
                                                AnsiString tempData = AnsiString((char*)RP.Data).SubString(3, 2);
                                                FLEDSet->UpDownDetailDelayBefore->Position = StrToIntDef(tempData, 0);
                                                tempData = AnsiString((char*)RP.Data).SubString(6, 2).Trim();
                                                FLEDSet->UpDownDetailDelayAfter->Position = StrToIntDef(tempData, 0);
                                            }
                                            //if(PorotocolType == 1)
                                            //{
                                                asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                     + AnsiString((char)RP.OPCode) + AnsiString((char*)&RP.Data[1]).SubString(1, 6) + "!]";
                                            //}
                                            //else
                                            //{
                                            //    asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                            //         + Byte2HexConvert(RP.OPCode) + " ";
                                            //    for(int j = 1; j <= 6; j++)
                                            //        asLogMessage = asLogMessage + Byte2HexConvert(RP.Data[j]) + " ";
                                            //    asLogMessage = asLogMessage + "21 5D";
                                            //}
                                        }
                                        else if(RP.Data[1] == 0x32 || RP.Data[1] == 0x33)
                                        {
                                            if(FDABITOption && RP.Rcnt > 4)
                                            {
                                                AnsiString tempData = AnsiString((char*)RP.Data).SubString(4, 1);
                                                FDABITOption->cbDubug->ItemIndex = StrToIntDef(tempData, 0);
                                                tempData = AnsiString((char*)RP.Data).SubString(6, 1).Trim();
                                                FDABITOption->cbBH1->ItemIndex = StrToIntDef(tempData, 0);
                                                tempData = AnsiString((char*)RP.Data).SubString(8, 1);
                                                FDABITOption->cbJ4->ItemIndex = StrToIntDef(tempData, 0);
                                                tempData = AnsiString((char*)RP.Data).SubString(10, 1);
                                                FDABITOption->cbBoardRate232->ItemIndex = StrToIntDef(tempData, 4);
                                                tempData = AnsiString((char*)RP.Data).SubString(12, 1);
                                                FDABITOption->cbBoardRateTTL->ItemIndex = StrToIntDef(tempData, 4);
                                                tempData = AnsiString((char*)RP.Data).SubString(14, 1);
                                                FDABITOption->cbBoardRate485->ItemIndex = StrToIntDef(tempData, 4);
                                                tempData = AnsiString((char*)RP.Data).SubString(16, 2);
                                                FDABITOption->laLabel10->Caption = tempData;
                                            }
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                + AnsiString((char)RP.OPCode) + AnsiString((char*)&RP.Data[1]).SubString(1, 16) + "!]";
                                        }
                                    }
                                }
                                else if(RP.OPCode == 'D')
                                {
                                    if(RP.Status == 'F')
                                    {
                                        if(PorotocolType == 1)
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                 + AnsiString((char)RP.OPCode) + AnsiString((char)RP.Data[1]) + AnsiString((char)RP.Status) + "!]";
                                        }
                                        else
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                                 + Byte2HexConvert(RP.OPCode) + " " + Byte2HexConvert(RP.Data[1]) + " " + Byte2HexConvert(RP.Status) + " 21 5D";
                                        }
                                    }
                                    else
                                    {
                                        if(RP.Data[1] == 0x31)
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                + AnsiString((char)RP.OPCode) + AnsiString((char*)&RP.Data[1]).SubString(1, 13) + "!]";
                                        }
                                        else
                                        {
                                            if(PorotocolType == 1)
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                                 + AnsiString((char)RP.OPCode) + AnsiString((char)RP.Data[1]) + AnsiString((char)RP.Status) + "!]";
                                        }
                                        else
                                        {
                                            asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                                 + Byte2HexConvert(RP.OPCode) + " " + Byte2HexConvert(RP.Data[1]) + " " + Byte2HexConvert(RP.Status) + " 21 5D";
                                        }
                                        }
                                    }
                                }
                                else
                                {
                                    if(PorotocolType == 1)
                                    {
                                        asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                             + AnsiString((char)RP.OPCode) + AnsiString((char)RP.Data[1]) + AnsiString((char)RP.Status) + "!]";
                                    }
                                    else
                                    {
                                        asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                             + Byte2HexConvert(RP.OPCode) + " " + Byte2HexConvert(RP.Data[1]) + " " + Byte2HexConvert(RP.Status) + " 21 5D";
                                    }
                                }
                            }catch(...){
                            }
                            ZeroMemory(&RP, sizeof(RP));
                        }
                        else
                            RP.CS = SynASCIIBCC;
                    }
                    else
                        ZeroMemory(&RP, sizeof(RP));
                    break;
                }
                case SynASCIIBCC :
                {
                    RP.Bcc[RP.BCCcnt++] = ch;
                    if(RP.BCCcnt >= sizeof(RP.Bcc))
                    {
                        try{
                            //if(asJohap != "" && asJohap != " " && PorotocolType == 1)
                            //    asLogMessage = asJohap;
                            if(RP.OPCode <= 0x31)
                            {
                                if(PorotocolType == 1)
                                {
                                    asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                         + AnsiString((char)RP.OPCode) + AnsiString((char)RP.Status) + "!]" + AnsiString((char)RP.Bcc[0]) + AnsiString((char)RP.Bcc[1]);
                                }
                                else
                                {
                                    asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                         + Byte2HexConvert(RP.OPCode) + " " + Byte2HexConvert(RP.Status) + " 21 5D " + Byte2HexConvert(RP.Bcc[0]) + " " + Byte2HexConvert(RP.Bcc[1]);
                                }
                            }
                            else if(RP.OPCode == 0x33)
                            {
                                if(RP.Data[1] == 0x33)
                                {
                                    if(PorotocolType == 1)
                                    {
                                        asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                             + AnsiString((char)RP.OPCode);
                                    }
                                    else
                                    {
                                        asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                             + Byte2HexConvert(RP.OPCode) + " ";
                                    }
                                    if(RP.Status == 'F')
                                    {
                                        if(PorotocolType == 1)
                                        {
                                            asLogMessage = asLogMessage + AnsiString((char)RP.Data[1]) + AnsiString((char)RP.Status) + "!]" + AnsiString((char)RP.Bcc[0]) + AnsiString((char)RP.Bcc[1]);
                                        }
                                        else
                                        {
                                            asLogMessage = asLogMessage + Byte2HexConvert(RP.OPCode) + " " + Byte2HexConvert(RP.Data[1]) + " " + Byte2HexConvert(RP.Status) + " 21 5D " + Byte2HexConvert(RP.Bcc[0]) + " " + Byte2HexConvert(RP.Bcc[1]);
                                        }
                                    }
                                    else
                                    {
                                        if(PorotocolType == 1)
                                        {
                                            asLogMessage = asLogMessage + AnsiString((char*)&RP.Data[1]).SubString(1, 57) + "!]" + AnsiString((char)RP.Bcc[0]) + AnsiString((char)RP.Bcc[1]);
                                        }
                                        else
                                        {
                                            for(int j = 1; j <= 57; j++)
                                                asLogMessage = asLogMessage + Byte2HexConvert(RP.Data[j]) + " ";
                                            asLogMessage = asLogMessage + "21 5D " + Byte2HexConvert(RP.Bcc[0]) + " " + Byte2HexConvert(RP.Bcc[1]);
                                        }
                                        if(FASCIISet)
                                            FASCIISet->mASCIIText11->Text = "![0032" + AnsiString((char *)&RP.Data[2]).SubString(1, 56) + "!]";
                                    }
                                }
                                else if(RP.Data[1] == 0x31)
                                {
                                    if(PorotocolType == 1)
                                    {
                                        asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                             + AnsiString((char)RP.OPCode) + AnsiString((char*)&RP.Data[1]).SubString(1, 15) + "!]";
                                    }
                                    else
                                    {
                                        asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                             + Byte2HexConvert(RP.OPCode) + " ";
                                        for(int j = 1; j <= 15; j++)
                                            asLogMessage = asLogMessage + Byte2HexConvert(RP.Data[j]) + " ";
                                        asLogMessage = asLogMessage + "21 5D";
                                    }
                                    if(FETC)
                                    {
                                        FETC->Label25->Caption = AnsiString((char*)&RP.Data[2]).SubString(1, 2) + "-" + AnsiString((char*)&RP.Data[2]).SubString(3, 2) + "-" + AnsiString((char*)&RP.Data[2]).SubString(5, 2) +
                                            " (" + GetDay(StrToIntDef((char)RP.Data[8], 0)) + ") " + AnsiString((char*)&RP.Data[2]).SubString(8, 2) + ":" + AnsiString((char*)&RP.Data[2]).SubString(10, 2) + ":" + AnsiString((char*)&RP.Data[2]).SubString(12, 2);
                                    }
                                }
                                else
                                {
                                    if(PorotocolType == 1)
                                    {
                                        asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                             + AnsiString((char)RP.OPCode) + AnsiString((char)RP.Data[1]) + " " + AnsiString((char)RP.Status) + "!]" + AnsiString((char)RP.Bcc[0]) + AnsiString((char)RP.Bcc[1]);
                                    }
                                    else
                                    {
                                        asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                         + Byte2HexConvert(RP.OPCode) + " " + Byte2HexConvert(RP.Data[1]) + " " + Byte2HexConvert(RP.Status) + " 21 5D " + Byte2HexConvert(RP.Bcc[0]) + " " + Byte2HexConvert(RP.Bcc[1]);
                                    }
                                }
                            }
                            else if(RP.OPCode == 0x39)
                            {
                                if(RP.Status == 'F')
                                {
                                    if(PorotocolType == 1)
                                    {
                                        asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                             + AnsiString((char)RP.OPCode) + AnsiString((char)RP.Status) + "!]";
                                    }
                                    else
                                    {
                                        asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                             + Byte2HexConvert(RP.OPCode) + " " + Byte2HexConvert(RP.Status) + " 21 5D";
                                    }
                                }
                                else
                                {
                                    asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                        + AnsiString((char)RP.OPCode);
                                    if(RP.Data[1] == 0x35 || RP.Data[1] == 0x36)
                                    {
                                        if(FFont && RP.Rcnt > 4)
                                        {
                                            AnsiString tempData = AnsiString((char*)RP.Data).SubString(3, 2);
                                            int iPos = 0;
                                            int iTextLen = 0;
                                            for(int j = 0; j < 2; j++)
                                            {
                                                for(int i = 0; i < 3; i++)
                                                {
                                                    FontFileNameVersion[j][i] = AnsiString((char*)&RP.Data[4 + iPos * 36]).SubString(1, 36);
                                                    asLogMessage += FontFileNameVersion[j][i];
                                                    iTextLen = FontFileNameVersion[j][i].Length();
                                                    if(iTextLen < 36)
                                                    {
                                                        for(int k = 0; k < 36 - iTextLen; k++)
                                                            asLogMessage += " ";
                                                    }
                                                    iPos++;
                                                }
                                            }
                                            asLogMessage += "!]";
                                        }
                                        else
                                            asLogMessage += AnsiString((char)RP.OPCode) + AnsiString((char*)&RP.Data[1]).SubString(1, RP.Rcnt) + "!]";
                                    }
                                    else
                                        asLogMessage += AnsiString((char)RP.OPCode) + AnsiString((char*)&RP.Data[1]).SubString(1, RP.Rcnt) + "!]";
                                }
                            }
                            else
                            {
                                if(PorotocolType == 1)
                                {
                                    asLogMessage = asLogMessage + "\r\n                \t Receive : ![" + AnsiString((char)RP.Add) + AnsiString((char)RP.SendID)
                                         + AnsiString((char)RP.OPCode) + AnsiString((char)RP.Data[1]) + " " + AnsiString((char)RP.Status) + "!]" + AnsiString((char)RP.Bcc[0]) + AnsiString((char)RP.Bcc[1]);
                                }
                                else
                                {
                                    asLogMessage = asLogMessage + "\r\n                \t Receive : 21 5B " + Byte2HexConvert(RP.Add) + " " + Byte2HexConvert(RP.SendID) + " "
                                         + Byte2HexConvert(RP.OPCode) + " " + Byte2HexConvert(RP.Data[1]) + " " + Byte2HexConvert(RP.Status) + " 21 5D " + Byte2HexConvert(RP.Bcc[0]) + " " + Byte2HexConvert(RP.Bcc[1]);
                                }
                            }
                        }catch(...){
                        }
                        ZeroMemory(&RP, sizeof(RP));
                    }
                    break;
                }
                default :
                {
                    ZeroMemory(&RP, sizeof(RP));
                    break;
                }
            }
        }
}
//---------------------------------------------------------------------------

AnsiString __fastcall TFormMain::VersionInfo(const AnsiString & sQuery)
{
        DWORD dwHandle = 0, dwVersionInfoSize;
        UINT uLength;
        LPVOID pFileInfo, ptr;
        AnsiString sOut; // 리턴될 버전 정보.

        AnsiString filename = Application->ExeName;

        dwVersionInfoSize = GetFileVersionInfoSize(filename.c_str(), &dwHandle);

        pFileInfo = (LPVOID) HeapAlloc(GetProcessHeap(), HEAP_ZERO_MEMORY, dwVersionInfoSize);

        GetFileVersionInfo(filename.c_str(), dwHandle, dwVersionInfoSize, pFileInfo);
        VerQueryValue(pFileInfo, TEXT("\\VarFileInfo\\Translation"), &ptr, &uLength);

        WORD *id = (WORD *) ptr;
        AnsiString szString = "\\StringFileInfo\\" + IntToHex(id[0], 4) + IntToHex(id[1], 4) + "\\" + sQuery;

        VerQueryValue(pFileInfo, szString.c_str(), &ptr, &uLength);
        sOut = AnsiString((char *) ptr);
        HeapFree(GetProcessHeap(), 0, pFileInfo );
        return sOut;
}
//---------------------------------------------------------------------------

AnsiString __fastcall TFormMain::GetDay(int iDay)
{
         // 현재 요일 String 리턴 함수
        AnsiString asDay = "";
        switch(iDay)
        {
            case 1:
                asDay = LangFile.FormProtocolDateItem1;
                break;
            case 2:
                asDay = LangFile.FormProtocolDateItem2;
                break;
            case 3:
                asDay = LangFile.FormProtocolDateItem3;
                break;
            case 4:
                asDay = LangFile.FormProtocolDateItem4;
                break;
            case 5:
                asDay = LangFile.FormProtocolDateItem5;
                break;
            case 6:
                asDay = LangFile.FormProtocolDateItem6;
                break;
            case 0:
            case 7:
                asDay = LangFile.FormProtocolDateItem7;
                break;
            default:
                asDay = LangFile.FormProtocolDateItem1;
                break;
        }
        return asDay;
}
//---------------------------------------------------------------------------

TColor __fastcall TFormMain::GetColor(int iColor)
{
         // 현재 요일 String 리턴 함수
        TColor rColor;
        switch(iColor)
        {
            case 1:
                rColor = clRed;
                break;
            case 2:
                rColor = clLime;
                break;
            case 3:
                rColor = clYellow;
                break;
            case 4:
                rColor = clBlue;
                break;
            case 5:
                rColor = (TColor)RGB(255, 0, 255);
                break;
            case 6:
                rColor = (TColor)RGB(0, 255, 255);
                break;
            case 7:
                rColor = clWhite;
                break;
            default:
                rColor = clBlack;
                break;
        }
        return rColor;
}
//---------------------------------------------------------------------------
                                           
AnsiString __fastcall TFormMain::GetDNS()
{
        // PC DNS 읽어오는 함수
        AnsiString result;
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
            result = FixedInfo->DnsServerList.IpAddress.String;
        //.........
        if(FixedInfo)
            GlobalFree( FixedInfo );
        return result;
}
//---------------------------------------------------------------------------

AnsiString __fastcall TFormMain::GetLocalIP()
{
        //현재 PC IP 얻어오는 함수
        AnsiString AddressStr;
        unsigned char TempValue[4];

        IP_ADAPTER_INFO AdapterInfo[16];

        DWORD AdapterBufLen = sizeof(AdapterInfo);

        GetAdaptersInfo(AdapterInfo, &AdapterBufLen);

        PIP_ADAPTER_INFO PointAdapterInfo = AdapterInfo;

        IP_ADDR_STRING *LocalNetworkAddr;
        IP_ADDR_STRING *LocalGateWayAddr;
        bool bFindAddr = false;
        do
        {
            LocalNetworkAddr = &PointAdapterInfo->IpAddressList;
            LocalGateWayAddr = &PointAdapterInfo->GatewayList;
            bFindAddr = false;
            while(LocalNetworkAddr)
            {
                AddressStr = LocalNetworkAddr->IpAddress.String;
                LocalNetworkAddr = LocalNetworkAddr->Next;
                LocalGateWayAddr = LocalGateWayAddr->Next;
                if(AddressStr != "0.0.0.0")
                    bFindAddr = true;
            }
            if(bFindAddr)
                break;
            else
                PointAdapterInfo = PointAdapterInfo->Next;

        }while(PointAdapterInfo);
        return AddressStr;
}
//---------------------------------------------------------------------------

bool __fastcall TFormMain::ComPortSet(BYTE ComKind, AnsiString PortUse, BYTE BaudRateUse, BYTE DTR, BYTE RTS, AnsiString IPAddress, int IPPort, bool bMessage)
{
        // 통신 연결 함수
        AnsiString asMessage = "";
        /*if(ComPort1->Connected  || IdUDPServer->Active)
            ComClose(ComKind);
        else if(DMClient)
        {
             if(DMClient->ClientSocket1->Socket->Connected)
                 ComClose(ComKind);
        }*/
        ComClose(ComKind);
        bool bDDNS = false;
        if(ComKind == 1)
        {
            if(DMClient)
            {
                delete DMClient;
                DMClient = NULL;
            }
            DMClient = new TDataModuleClient(this);
            bool tempMsg = false;
            try{
                int tempIP = StrToIntDef(IPAddress.SubString(1, IPAddress.Pos(".") - 1), -1);
                if(tempIP < 0)
                    bDDNS = true;
                if(bDDNS)
                {
                    try{
                        SchBmpFile = false;
                        DNSAddress = "";
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
                                        }
                                        break;
                                    }
                                    if(DNSConnect)
                                        break;
                                }
                                tempMsg = false;
                            }
                        }catch(...){
                            asMessage = "IP:" + DMClient->ClientSocket1->Address + ", PORT:" + IntToStr(DMClient->ClientSocket1->Port) + " Open [FAIL]";
                            AddLog(asMessage, clRed);
                            if(bMessage)
                                Message("\r\n" + asMessage + "\r\n", LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                            DNSConnect = false;
                            DNSAddress = "";
                            return false;
                        }
                        asMessage = "IP:" + DMClient->ClientSocket1->Address + ", PORT:" + IntToStr(DMClient->ClientSocket1->Port) + " Open [FAIL]";
                        AddLog(asMessage, clRed);
                        if(tempMsg && bMessage)
                            Message("\r\n" + asMessage + "\r\n", LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                        DNSConnect = false;
                        DNSAddress = "";
                        return false;
                    }
                    if(DNSConnect)
                    {
                        DMClient->ClientSocket1->Address = DNSAddress;
                        DMClient->ClientSocket1->Port = IPPort;
                        DNSConnect = false;
                    }
                    else
                    {
                        DMClient->ClientSocket1->Address = "127.0.0.1";
                        DMClient->ClientSocket1->Port = IPPort;
                    }
                }
                else
                {
                    DMClient->ClientSocket1->Address = IPAddress;
                    DMClient->ClientSocket1->Port = IPPort;
                }
            }catch(...){
                asMessage = "IP:" + DMClient->ClientSocket1->Address + ", PORT:" + IntToStr(DMClient->ClientSocket1->Port) + " Open [FAIL]";
                AddLog(asMessage, clRed);
                Message("\r\n" + asMessage + "\r\n", LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                return false;
            }
            try{
                DNSConnect = false;
                DMClient->ClientSocket1->Open();
                LANDelayCount(ServerCommOpenDelay);
                if(!DNSConnect)
                {
                    asMessage = "IP:" + DMClient->ClientSocket1->Address + ", PORT:" + IntToStr(DMClient->ClientSocket1->Port) + " Open [FAIL]";
                    AddLog(asMessage, clRed);
                    if(bMessage)
                        Message("\r\n" + asMessage + "\r\n", LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                    return false;
                }
                else
                {
                    asMessage = "IP:" + DMClient->ClientSocket1->Address + ", PORT:" + IntToStr(DMClient->ClientSocket1->Port) + " Open [OK]";
                    AddLog(asMessage, clBlack);
                    DNSConnect = false;
                }
            }catch(...){
                asMessage = "IP:" + DMClient->ClientSocket1->Address + ", PORT:" + IntToStr(DMClient->ClientSocket1->Port) + " Open [FAIL]";
                AddLog(asMessage, clRed);
                if(bMessage)
                    Message("\r\n" + asMessage + "\r\n", LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                return false;
            }
        }
        else if(ComKind == 2)
        {
            try{
                if(ServerSocket1->Socket->Connected)
                //{
                //    DNSConnect = false;
                //    SchBmpFile = false;
                    ServerSocket1->Close();
                //    LANDelayCount(5000);
                //}
                ServerSocket1->Port = ServerPort;
                SchBmpFile = false;
                DNSConnect = false;
                ServerSocket1->Open();
                LANDelayCount(ServerCommOpenDelay);
                if(!DNSConnect)
                {
                    asMessage = "Local PORT:" + IntToStr(ServerSocket1->Port) + " Open [FAIL]";
                    AddLog(asMessage, clRed);
                    if(bMessage)
                        Message("\r\n" + asMessage + "\r\n", LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                    return false;
                }
                else
                {
                    DNSConnect = false;
                    asMessage = "Local PORT:" + IntToStr(ServerSocket1->Port) + " Open [OK]";
                    AddLog(asMessage, clBlack);

                }
            }catch(...){
                asMessage = "Local PORT:" + IntToStr(ServerSocket1->Port) + " Open [FAIL]";
                AddLog(asMessage, clRed);
                if(bMessage)
                    Message("\r\n" + asMessage + "\r\n", LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                return false;
            }
        }
        else if(ComKind == 3)
        {
            bool tempMsg = false;
            try{
                bDDNS = false;
                int tempIP = StrToIntDef(UDPIPAddress.SubString(1, UDPIPAddress.Pos(".") - 1), -1);
                if(tempIP < 0)
                    bDDNS = true;
                if(bDDNS)
                {
                    try{
                        SchBmpFile = false;
                        DNSAddress = "";
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
                                        }
                                        break;
                                    }
                                    if(DNSConnect)
                                        break;
                                }
                                tempMsg = false;
                            }
                        }catch(...){
                            AddLog("IP:" + DNSAddress + ", Remote PORT:" + DSPPort + ", Local PORT:" + IntToStr(SH->Port) + " Open [FAIL]", clRed);
                            if(bMessage)
                                Message("\r\n" + LangFile.Message05 + "\r\n", LangFile.MessageButton04, "", "", LangFile.Message05.Length() - 2, 3, 1, clRed);
                            DNSConnect = false;
                            DNSAddress = "";
                            return false;
                        }
                        AddLog("IP:" + DNSAddress + ", Remote PORT:" + DSPPort + ", Local PORT:" + IntToStr(SH->Port) + " Open [FAIL]", clRed);
                        if(bMessage)
                            Message("\r\n" + LangFile.Message05 + "\r\n", LangFile.MessageButton04, "", "", LangFile.Message05.Length() - 2, 3, 1, clRed);
                        if(tempMsg)
                        {
                            DNSConnect = false;
                            DNSAddress = "";
                            return false;
                        }
                    }
                    if(DNSConnect)
                    {
                        DNSConnect = false;
                        asUDPIPAddress = DNSAddress;
                    }
                    else
                        asUDPIPAddress = "127.0.0.1";
                }
                else
                    asUDPIPAddress = UDPIPAddress;
            }catch(...){
                AddLog("UDP IP:" + asUDPIPAddress + ", Remote PORT:" + DSPPort + ", Local PORT:" + IntToStr(SH->Port) + " UDP Open [FAIL]", clRed);
                if(bMessage)
                    Message("\r\n" + LangFile.Message05 + "\r\n", LangFile.MessageButton04, "", "", LangFile.Message05.Length() - 2, 3, 1, clRed);
                return false;
            }
            SchBmpFile = false;
            DNSConnect = false;
            try{
                if(!SH)
                    SH = IdUDPServer->Bindings->Add();
                SH->Port = SRCPort;
                IdUDPServer->Active = true;
                //if(bMessage)
                    AddLog("UDP IP:" + asUDPIPAddress + ", Remote PORT:" + DSPPort + ", Local PORT:" + IntToStr(SH->Port) + " UDP Open [OK]", clBlack);
            }catch(...){
                AddLog("UDP IP:" + asUDPIPAddress + ", Remote PORT:" + DSPPort + ", Local PORT:" + IntToStr(SH->Port) + " UDP Open [FAIL]", clRed);
            }
        }
        else
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
                    asBR = ": 921600bps";
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
            if(!ComPort1->Connected)
            {
                try{
                    ComPort1->Open();
                    asMessage = ComPort1->Port + " " + asBR + ", 8, 1, N - ComPort Open [OK]";
                    //if(bMessage)
                        AddLog(asMessage, clBlack);
                }catch(...){
                    asMessage = ComPort1->Port + " " + asBR + ", 8, 1, N - ComPort Open [FAIL]";
                    AddLog(asMessage, clRed);
                    if(bMessage)
                        Message("\r\n" + asMessage + "\r\n", LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                    return false;
                }
            }
        }
        return true;
}
//---------------------------------------------------------------------------

bool __fastcall TFormMain::ComClose(BYTE ComKind)
{
        // 통신 닫는 함수
        if(DelayRun == 1 && DelayTime == 0)
            NoneDelayCount(300);
        AnsiString asMessage = "";
        try{
            if(IdUDPServer->Active)
                IdUDPServer->Active = false;
        }catch(...){
        }
        if(ComKind == 1)
        {
            if(DMClient)
            {
                try{
                    DMClient->ClientSocket1->Close();
                    for(int i = 0;i < 1000;i++)
                    {
                        if(DMClient->ClientSocket1->Socket->Connected)
                            NoneDelayCount(10);
                        else
                            break;
                    }
                    asMessage = "IP:" + DMClient->ClientSocket1->Address + ", PORT:" + IntToStr(DMClient->ClientSocket1->Port) + " Close [OK]";
                    AddLog(asMessage, clBlack);
                }catch(...){
                    asMessage = "IP:" + DMClient->ClientSocket1->Address + ", PORT:" + IntToStr(DMClient->ClientSocket1->Port) + " Close [FAIL]";
                    AddLog(asMessage, clRed);
                    Message("\r\n" + asMessage + "\r\n", LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                    return false;
                }
            }
        }
        else if(ComKind == 2)
        {
            try{
                ServerSocket1->Close();
                asMessage = "Local PORT:" + IntToStr(ServerSocket1->Port) + " Close [OK]";
                AddLog(asMessage, clBlack);
            }catch(...){
                asMessage = "Local PORT:" + IntToStr(ServerSocket1->Port) + " Close [FAIL]";
                AddLog(asMessage, clRed);
                return false;
            }
        }
        else if(ComKind == 3)
        {
            try{
                IdUDPServer->Active = false;
                if(SH)
                    AddLog("UDP IP:" + asUDPIPAddress + ", Local PORT:" + IntToStr(SH->Port)+ " UDP Close : [OK]", clBlack);
            }catch(...){
                asMessage = "UDP IP:" + asUDPIPAddress + ", Local PORT:" + IntToStr(SH->Port)+ " UDP Close : [FAIL]";
                if(SH)
                    AddLog(asMessage, clRed);
                Message("\r\n" + asMessage + "\r\n", LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                return false;
            }
        }
        else
        {
            try{
                ComPort1->Close();
                asMessage = ComPort1->Port + " Close [OK]";
                AddLog(asMessage, clBlack);
            }catch(...){
                asMessage = ComPort1->Port + " Close [FAIL]";
                AddLog(asMessage, clRed);
                Message("\r\n" + asMessage + "\r\n", LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                return false;
            }
        }
        return true;
}
//---------------------------------------------------------------------------

int __fastcall TFormMain::FindItemIndex(AnsiString Data, int Col)
{
        // 효과,방향 인덱스 리턴 함수
        switch (Col)
        {
            case 1:
                return -1;
            case 2:
            {
                for(int i = 0; i < EffectTypeCount; i++)
                    if(!Data.AnsiCompare(AnsiString(EffectType[i])))
                        return i;
            }
            break;
            case 3:
            {
                for(int i = 0; i < DirTypeCount; i++)
                    if(!Data.AnsiCompare(AnsiString(DirType[i])))
                        return i;
            }
            break;
            case 4:
                return -1;
            case 5:
                return -1;
            case 6:
                return -1;
        }
        return -1;
}
//---------------------------------------------------------------------------

BYTE __fastcall TFormMain::InputSchEffect(TGEffectType eEff, TGEffectDirection dDirect)
{
        // 전광판 효과값 리턴 함수
        BYTE ResultEffect = 0x00;
        switch(eEff)
        {
            case etNormal:
            {
                switch(dDirect)
                {
                    case edBrightUp:
                        ResultEffect = BrightUp;
                    break;
                    case edBrightDown:
                        ResultEffect = BrightDown;
                    break;
                    case edHoriMirror:
                        ResultEffect = HoriMirror;
                    break;
                    case edVertMirror:
                        ResultEffect = VertMirror;
                    break;
                    default:
                        ResultEffect = None;
                    break;
                }
            }
            break;
            case etRandom:
            {
                switch(dDirect)
                {
                    case edOrder:
                        ResultEffect = (Effect)Order;
                    break;
                    default:
                        ResultEffect = (Effect)Random;
                    break;
                }
            }
            break;
            case etShift:
            {
                switch(dDirect)
                {
                    case edRight:
                        ResultEffect = ShiftRight;
                    break;
                    case edUp:
                        ResultEffect = ShiftUp;
                    break;
                    case edDown:
                        ResultEffect = ShiftDown;
                    break;
                    case edLeftAdd:
                        ResultEffect = ShiftLeftAdd;
                    break;
                    case edUpDown:
                        ResultEffect = ShiftUpDown;
                    break;
                    default:
                        ResultEffect = ShiftLeft;
                    break;
                }
            }
            break;
            case et3DShift:
            {
                switch(dDirect)
                {
                    case edRight:
                        ResultEffect = Shift3DRight;
                    break;
                    case edUp:
                        ResultEffect = Shift3DUp;
                    break;
                    case edDown:
                        ResultEffect = Shift3DDown;
                    break;
                    default:
                        ResultEffect = Shift3DLeft;
                    break;
                }
            }
            break;
            case etWipe:
            {
                switch(dDirect)
                {
                    case edRight:
                        ResultEffect = WipeRight;
                    break;
                    case edUp:
                        ResultEffect = WipeUp;
                    break;
                    case edDown:
                        ResultEffect = WipeDown;
                    break;
                    case edLeftUp:
                        ResultEffect = WipeLeftUp;
                    break;
                    case edRightDown:
                        ResultEffect = WipeRightDown;
                    break;
                    default:
                        ResultEffect = WipeLeft;
                    break;
                }
            }
            break;
            case etBlind:
            {
                switch(dDirect)
                {
                    case edRight:
                        ResultEffect = BlindRight;
                    break;
                    case edUp:
                        ResultEffect = BlindUp;
                    break;
                    case edDown:
                        ResultEffect = BlindDown;
                    break;
                    case edLeftUp:
                        ResultEffect = BlindLeftUp;
                    break;
                    case edRightDown:
                        ResultEffect = BlindRightDown;
                    break;
                    default:
                        ResultEffect = BlindLeft;
                    break;
                }
            }
            break;
            case etCurtain:
            {
                switch(dDirect)
                {
                    case edHoriCenter:
                        ResultEffect = CurtainHoriCenter;
                    break;
                    case edVerSide:
                        ResultEffect = CurtainVertSide;
                    break;
                    case edVerCenter:
                        ResultEffect = CurtainVertCenter;
                    break;
                    case edHoriSideCenter:
                        ResultEffect = CurtainHoriSideCenter;
                    break;
                    case edVertSideCenter:
                        ResultEffect = CurtainVertSideCenter;
                    break;
                    default:
                        ResultEffect = CurtainHoriSide;
                    break;
                }
            }
            break;
            case etScaleIn:
            {
                switch(dDirect)
                {
                    case edLeft:
                        ResultEffect = ScaleInLeft;
                    break;
                    case edRight:
                        ResultEffect = ScaleInRight;
                    break;
                    case edUp:
                        ResultEffect = ScaleInUp;
                    break;
                    case edDown:
                        ResultEffect = ScaleInDown;
                    break;
                    default:
                        ResultEffect = ScaleInLeftUp;
                    break;
                }
            }
            break;
            case etScaleOut:
            {
                switch(dDirect)
                {
                    case edLeft:
                        ResultEffect = ScaleOutLeft;
                    break;
                    case edRight:
                        ResultEffect = ScaleOutRight;
                    break;
                    case edUp:
                        ResultEffect = ScaleOutUp;
                    break;
                    case edDown:
                        ResultEffect = ScaleOutDown;
                    break;
                    default:
                        ResultEffect = ScaleOutRightDown;
                    break;
                }
            }
            break;
            case etRotate:
            {
                switch(dDirect)
                {
                    case edReverseClock:
                        ResultEffect = RotateReverseClock;
                    break;
                    case edReverseClock2:
                        ResultEffect = Rotate2ReverseClock;
                    break;
                    case edClock2:
                        ResultEffect = Rotate2Clock;
                    break;
                    default:
                        ResultEffect = RotateClock;
                    break;
                }
            }
            break;
            case etBlink:
            {
                switch(dDirect)
                {
                    case edGreen:
                        ResultEffect = BlinkGreen;
                    break;
                    case edBlue:
                        ResultEffect = BlinkBlue;
                    break;
                    case edWhite:
                        ResultEffect = BlinkWhite;
                    break;
                    case edRGB:
                        ResultEffect = BlinkRGB;
                    break;
                    default:
                        ResultEffect = BlinkRed;
                    break;
                }
            }
            break;
            case etBlinkReverse:
            {
                switch(dDirect)
                {
                    case edGreen:
                        ResultEffect = BlinkReverseGreen;
                    break;
                    case edBlue:
                        ResultEffect = BlinkReverseBlue;
                    break;
                    case edWhite:
                        ResultEffect = BlinkReverseWhite;
                    break;
                    case edRGB:
                        ResultEffect = BlinkReverseRGB;
                    break;
                    case edAll:
                        ResultEffect = BlinkReverseAll;
                    break;
                    default:
                        ResultEffect = BlinkReverseRed;
                    break;
                }
            }
            break;
            case etLineTest:
            {
                switch(dDirect)
                {
                    case edRight:
                        ResultEffect = LineRight;
                    break;
                    case edUp:
                        ResultEffect = LineUp;
                    break;
                    case edDown:
                        ResultEffect = LintDown;
                    break;
                    case edLeftUp:
                        ResultEffect = LineLeftUp;
                    break;
                    case edLeftDown:
                        ResultEffect = LineLeftDown;
                    break;
                    case edRightUp:
                        ResultEffect = LineRightUp;
                    break;
                    case edRightDown:
                        ResultEffect = LineRightDown;
                    break;
                    default:
                        ResultEffect = LineLeft;
                    break;
                }
            }
            break;
            default:
                ResultEffect = 0x00;
            break;
        }
        return ResultEffect;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::cbEntEffectClick(TObject *Sender)
{
        TGEffectType ED = (TGEffectType)FindItemIndex(cbEntEffect->Text, 2);
        cbEntDirect->Clear();
        cbEntDirect->ItemIndex = -1;
        switch(ED)
        {
            case etNormal:
                cbEntDirect->Items->Add(DirType[edNone]);
            break;
            case etShift:
                cbEntDirect->Items->Add(DirType[edLeft]);
            break;
            case etWipe:
            case et3DShift:
                cbEntDirect->Items->Add(DirType[edLeft]);
            break;
            case etBlind:
                cbEntDirect->Items->Add(DirType[edLeft]);
            break;
            case etCurtain:
                cbEntDirect->Items->Add(DirType[edHoriSide]);
            break;
            case etScaleIn:
                cbEntDirect->Items->Add(DirType[edLeftUp]);
            break;
            case etScaleOut:
                cbEntDirect->Items->Add(DirType[edRightDown]);
            break;
            case etRotate:
                cbEntDirect->Items->Add(DirType[edReverseClock]);
            break;
            case etBlink:
            case etBlinkReverse:
                cbEntDirect->Items->Add(DirType[edRed]);
            break;
            case etLineTest:
                cbEntDirect->Items->Add(DirType[edLeft]);
            break;
            case etRandom:
                cbEntDirect->Items->Add(DirType[edRandom]);
            break;
        }
        cbEntDirect->ItemIndex = 0;
}
//---------------------------------------------------------------------------

BYTE __fastcall TFormMain::SetDelayTime(int DelayTime)
{
        // 딜레이값 리턴 함수
        BYTE bResult = 0x00;
        switch(DelayTime)
        {
            case 0:
            case 2:
            case 4:
            case 8:
            case 10:
            case 15:
            case 20:
            case 30:
            case 40:
            case 60:
            case 80:
                bResult = DelayTime * 2;
            break;
            case 120:
                bResult = 90;
            break;
            case 180:
                bResult = 91;
            break;
            case 300:
                bResult = 92;
            break;
            case 600:
                bResult = 93;
            break;
            case 1800:
                bResult = 94;
            break;
            case 3600:
                bResult = 95;
            break;
            case 10800:
                bResult = 96;
            break;
            case 18000:
                bResult = 97;
            break;
            case 32400:
                bResult = 98;
            break;
            default:
                bResult = DelayTime * 2;
            break;
        }
        return bResult;
}
//---------------------------------------------------------------------------

BYTE *__fastcall TFormMain::SetLEDModuleValue(BYTE bDispType, BYTE bDispColor, BYTE bScanOder)
{
        // LED 모듈 셋팅 함수
        AnsiString asDspType3[] =
            {"16D-P16D1S11","16D-P16D1S10-1","16D-P16D1S21-1","08D-P32D1S22-9<1","08D-P16D2S10-1^9",
             "08D-P16D2S11-1^9","08D-P16D2S31-9^1","08D-P16D2S23-1^2","08D-P32D1S31","08D-HUB75-D2-R","08D-P64D1S31","08D-P64D1S91-11",
             "04D-P32D2S31-2<1^4<3","04D-P32D2S41","04D-P32D2S51","04D-P16D4S21-1^5^9^13","01D-P64D4S21-4^8^12^16",
             "01D-P128D2S31"};
        AnsiString asDspTypeF[17];
        AnsiString asDspType8F[] =
           {"32D-P16D1S11","16D-P16D1S10-1","16D-P16D1S11","16D-P16D1S21","16D-P16D1S31","16D-P16D1S41","08D-P16D2S10-1^9","08D-P16D2S11-1^9","08D-P16D2S21","08D-P16D2S23-1^2",//10
            "08D-P16D2S31-9^1","08D-P16D2S81","08D-P32D1S11","08D-P32D1S12","08D-P32D1S22-9<1","08D-P32D1S31","08D-P32D1S32","08D-P64D1S21","08D-P64D1S31","08D-P64D1S41-11","08D-P64D1S61","08D-P64D1S91-11","08D-P128D1S42","08D-P128D1S51",//24
            "04D-P16D4S11-1^5^9^13","04D-P16D4S21-1^5^9^13","04D-P32D2S11","04D-P32D2S21","04D-P32D2S31-2<1^4<3","04D-P32D2S41","04D-P32D2S51","04D-P32D2S61","04D-P32D2S71","04D-P64D1S11","04D-D2-SA1-A4E8A8E8A4","04D-D2-SA2-E8A8E8A8",//36
            "02D-P128D2S21","02D-P64D2S21","02D-P64D2S31","02D-P64D2S41","02D-P256D2S41","01D-P64D4S21-4^8^12^16","01D-P128D2S21","01D-P128D2S31"};
        int tempItemCnt = 0;
        AnsiString asDisp = "";
        /*if(ProgramType == e2BPP)
            asDisp = asDspType3[bDispType];
        else
        {
            if(Setup.ProgramType == e24BPP)
            {
                int tempCnt = 0;
                for(int  i = 0; i < 30; i++)
                {
                    if(i == 1 || i == 8 || i == 9 || i == 10 || i == 12 || i == 13 || i == 14 || i == 19 || i == 21 || i == 22 || i == 23 || i == 25 || i == 28 || i == 29)
                        continue;
                    asDspTypeF[tempCnt++] = asDspType8F[i];
                }
                asDisp = asDspTypeF[bDispType];
            }
            else*/
                asDisp = asDspType8F[bDispType];
        //}
        BYTE LEDModuleData[7];
        ZeroMemory(LEDModuleData, sizeof(LEDModuleData));
        /*if(Setup.ProgramType == e2BPP)
        //{
            switch(bDispType)
            {
                case 0:
                    LEDModuleData[0] = 0x10;
                break;
                case 1:
                    LEDModuleData[0] = 0x0F;
                break;
                case 4:
                    LEDModuleData[0] = 0x0B;
                break;
                case 7:
                    LEDModuleData[0] = 0x09;
                break;
                case 9:
                    LEDModuleData[0] = 0x0c;
                break;
                case 15:
                    LEDModuleData[0] = 0x06;
                break;
                case 17:
                    LEDModuleData[0] = 0x04;
                break;
                default:
                    LEDModuleData[0] = 0x00;
                break;
                /*case 0:
                    LEDModuleData[0] = 16;
                break;
                case 1:
                    LEDModuleData[0] = 15;
                break;
                case 5:
                    LEDModuleData[0] = 11;
                break;
                case 6:
                    LEDModuleData[0] = 10;
                break;
                case 13:
                    LEDModuleData[0] = 6;
                break;
                case 14:
                    LEDModuleData[0] = 4;
                break;
                default:
                    LEDModuleData[0] = 0;
                break;
            }
        }*/
        LEDModuleData[0] = 0;
        int iPos = asDisp.Pos("D-");
        int iPos2 = 0;
        AnsiString tempDisp = "";
        if(iPos)
            LEDModuleData[1] = StrToIntDef(asDisp.SubString(1, iPos - 1), 16);
        else
            LEDModuleData[1] = 16;
        iPos = asDisp.Pos("-P") + 3;
        if(iPos)
        {
            iPos2 = asDisp.Pos("S");
            tempDisp = asDisp.SubString(iPos + 2, iPos2 - (iPos + 2));
            LEDModuleData[2] = StrToIntDef(tempDisp, 1);
            iPos = asDisp.Pos("S");
            if(iPos)
            {
                tempDisp = asDisp.SubString(iPos + 1, 2);
                LEDModuleData[3] = StrToIntDef("0x" + tempDisp, 0);
                if(ProgramType != e2BPP)
                    LEDModuleData[4] = 1 + bDispColor;
                else
                    LEDModuleData[4] = 1;
            }
            else
            {
                LEDModuleData[3] = 0;
                LEDModuleData[4] = 1;
            }
            LEDModuleData[5] = 1;
        }
        else
        {
            LEDModuleData[2] = 1;
            LEDModuleData[3] = 0;
            LEDModuleData[4] = 1;
            LEDModuleData[5] = 1;
        }
        if(bScanOder >= 1)
        {
            if(bDispType == 31)
            {
                if(bScanOder == 2)
                    LEDModuleData[5] = 0x03;
                else
                    LEDModuleData[5] = 0x02;
            }
            else if(bDispType == 17)
            {
                if(bScanOder == 2)
                    LEDModuleData[5] = 0x06;
                else
                    LEDModuleData[5] = 0x05;
            }
            else
                LEDModuleData[5] = 0x04;
        }
        else
            LEDModuleData[5] = 0x01;
        if(bDispType == 0)
        {
            LEDModuleData[1] = 0x20;
            LEDModuleData[2] = 0x01;
            LEDModuleData[3] = 0x11;
            LEDModuleData[4] = 1 + bDispColor;
            LEDModuleData[5] = 0x01;
        }
        else if(bDispType == 6)
        {
            LEDModuleData[1] = 0x08;
            LEDModuleData[2] = 0x02;
            LEDModuleData[3] = 0x10;
            LEDModuleData[4] = 1 + bDispColor;
            LEDModuleData[5] = 0x02;
        }
        else if(bDispType == 34)
        {
            LEDModuleData[1] = 0x04;
            LEDModuleData[2] = 0x02;
            LEDModuleData[3] = 0xA1;
            LEDModuleData[4] = 1 + bDispColor;
            LEDModuleData[5] = 0x03;
        }
        else if(bDispType == 35)
        {
            LEDModuleData[1] = 0x04;
            LEDModuleData[2] = 0x02;
            LEDModuleData[3] = 0xA2;
            LEDModuleData[4] = 1 + bDispColor;
            LEDModuleData[5] = 0x01;
        }
        else if(bDispType == 40)
        {
            LEDModuleData[1] = 0x02;
            LEDModuleData[2] = 0x02;
            LEDModuleData[3] = 0x41;
            LEDModuleData[4] = 1 + bDispColor;
            LEDModuleData[5] = 0x04;
        }
        else if(bDispType == 42)
        {
            LEDModuleData[1] = 0x01;
            LEDModuleData[2] = 0x02;
            LEDModuleData[3] = 0x21;
            LEDModuleData[4] = 1 + bDispColor;
            LEDModuleData[5] = 0x01;
        }
        else if(bDispType == 43)
        {
            LEDModuleData[1] = 0x01;
            LEDModuleData[2] = 0x02;
            LEDModuleData[3] = 0x31;
            LEDModuleData[4] = 1 + bDispColor;
            LEDModuleData[5] = 0x01;
        }
        return LEDModuleData;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::cbEntEffectDropDown(TObject *Sender)
{
        if(cbEntEffect->Items->IndexOf(cbEntEffect->Text) > -1)
            cbEntEffect->ItemIndex = cbEntEffect->Items->IndexOf(cbEntEffect->Text);
        else
            cbEntEffect->ItemIndex = 0;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::cbEntDirectDropDown(TObject *Sender)
{
        AnsiString asED = cbEntDirect->Text;
        cbEntDirect->Clear();
        if(cbEntEffect->Text == EffectType[etNormal])
        {
            for(int i = edNone; i < 5; i++)
                cbEntDirect->Items->Add(DirType[i]);
        }
        else if(cbEntEffect->Text == EffectType[etShift])
        {
            for(int i = edLeft; i < edLeft + 6; i++)
            {
                if((TGEffectType)i == edLeftUp)
                    continue;
                if((TGEffectType)i == edUpDown)
                    continue;
                cbEntDirect->Items->Add(DirType[i]);
            }
        }
        else if(cbEntEffect->Text == EffectType[etWipe] ||
            cbEntEffect->Text == EffectType[etBlind])
        {
            for(int i = edLeft; i < edLeft + 4; i++)
                cbEntDirect->Items->Add(DirType[i]);
        }
        else if(cbEntEffect->Text == EffectType[etCurtain])
        {
            for(int i = edHoriSide; i < edHoriSide + 4; i++)
                cbEntDirect->Items->Add(DirType[i]);
        }
        else if(cbEntEffect->Text == EffectType[etScaleIn] ||
            cbEntEffect->Text == EffectType[etScaleOut])
        {
            for(int i = edLeft; i < edLeft + 4; i++)
                cbEntDirect->Items->Add(DirType[i]);
            if(cbEntEffect->Text == EffectType[etScaleIn])
                cbEntDirect->Items->Add(DirType[edLeftUp]);
            else
                cbEntDirect->Items->Add(DirType[edRightDown]);
        }
        else if(cbEntEffect->Text == EffectType[etRotate])
        {
            for(int i = edReverseClock; i < edReverseClock + 4; i++)
                cbEntDirect->Items->Add(DirType[i]);
        }
        else if(cbEntEffect->Text == EffectType[etBlink])
        {
            for(int i = edRed; i < edRed + 5; i++)
                cbEntDirect->Items->Add(DirType[i]);
        }
        else if(cbEntEffect->Text == EffectType[etBlinkReverse])
        {
            for(int i = edRed; i < edRed + 6; i++)
                cbEntDirect->Items->Add(DirType[i]);
        }
        else if(cbEntEffect->Text == EffectType[etLineTest])
        {
            for(int i = edLeft; i < edLeft + 10; i++)
            {
                if((TGEffectType)i == edLeftUp || (TGEffectType)i == edUpDown || (TGEffectType)i == edLeftAdd)
                    continue;
                if((TGEffectType)i == edLeft || (TGEffectType)i == edDown || (TGEffectType)i == edRightDown)
                    cbEntDirect->Items->Add(DirType[i]);
            }
        }
        else if(cbEntEffect->Text == EffectType[etRandom])
        {
            for(int i = edOrder + 1; i < edOrder + 2; i++)
                cbEntDirect->Items->Add(DirType[i]);
        }
        else if(cbEntEffect->Text == EffectType[et3DShift])
            cbEntDirect->Items->Add(DirType[edLeft]);
        if(cbEntDirect->Items->IndexOf(asED) > -1)
            cbEntDirect->ItemIndex = cbEntDirect->Items->IndexOf(asED);
        else
            cbEntDirect->ItemIndex = 0;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::cbLasEffectClick(TObject *Sender)
{
        TGEffectType ED = (TGEffectType)FindItemIndex(cbLasEffect->Text, 2);
        cbLasDirect->Clear();
        cbLasDirect->ItemIndex = -1;
        switch(ED)
        {
            case etNormal:
                cbLasDirect->Items->Add(DirType[edNone]);
            break;
            case etShift:
            case et3DShift:
                cbLasDirect->Items->Add(DirType[edLeft]);
            break;
            case etWipe:
                cbLasDirect->Items->Add(DirType[edLeft]);
            break;
            case etBlind:
                cbLasDirect->Items->Add(DirType[edLeft]);
            break;
            case etCurtain:
                cbLasDirect->Items->Add(DirType[edHoriSide]);
            break;
            case etScaleIn:
                cbLasDirect->Items->Add(DirType[edLeftUp]);
            break;
            case etScaleOut:
                cbLasDirect->Items->Add(DirType[edRightDown]);
            break;
            case etRotate:
                cbLasDirect->Items->Add(DirType[edReverseClock]);
            break;
            case etBlink:
            case etBlinkReverse:
                cbLasDirect->Items->Add(DirType[edRed]);
            break;
            case etLineTest:
                cbLasDirect->Items->Add(DirType[edLeft]);
            break;
            case etRandom:
                cbLasDirect->Items->Add(DirType[edRandom]);
            break;
            default:
                cbLasDirect->Items->Add(LangFile.NoEffect);
            break;
        }
        cbLasDirect->ItemIndex = 0;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::cbLasEffectDropDown(TObject *Sender)
{
        if(cbLasEffect->Items->IndexOf(cbLasEffect->Text) > -1)
            cbLasEffect->ItemIndex = cbLasEffect->Items->IndexOf(cbLasEffect->Text);
        else
            cbLasEffect->ItemIndex = 0;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::cbLasDirectDropDown(TObject *Sender)
{
        AnsiString asED = cbLasDirect->Text;
        cbLasDirect->Clear();
        if(cbLasEffect->Text == EffectType[etNormal])
        {
            for(int i = edNone; i < 5; i++)
                cbLasDirect->Items->Add(DirType[i]);
        }
        else if(cbLasEffect->Text == EffectType[etShift])
        {
            for(int i = edLeft; i < edLeft + 6; i++)
            {
                if((TGEffectType)i == edLeftUp)
                    continue;
                if((TGEffectType)i == edUpDown)
                    continue;
                cbLasDirect->Items->Add(DirType[i]);
            }
        }
        else if(cbLasEffect->Text == EffectType[etWipe] ||
            cbLasEffect->Text == EffectType[etBlind])
        {
            for(int i = edLeft; i < edLeft + 4; i++)
                cbLasDirect->Items->Add(DirType[i]);
        }
        else if(cbLasEffect->Text == EffectType[etCurtain])
        {
            for(int i = edHoriSide; i < edHoriSide + 4; i++)
                cbLasDirect->Items->Add(DirType[i]);
        }
        else if(cbLasEffect->Text == EffectType[etScaleIn] ||
            cbLasEffect->Text == EffectType[etScaleOut])
        {
            for(int i = edLeft; i < edLeft + 4; i++)
                cbLasDirect->Items->Add(DirType[i]);
            if(cbLasEffect->Text == EffectType[etScaleIn])
                cbLasDirect->Items->Add(DirType[edLeftUp]);
            else
                cbLasDirect->Items->Add(DirType[edRightDown]);
        }
        else if(cbLasEffect->Text == EffectType[etRotate])
        {
            for(int i = edReverseClock; i < edReverseClock + 4; i++)
                cbLasDirect->Items->Add(DirType[i]);
        }
        else if(cbLasEffect->Text == EffectType[etBlink])
        {
            for(int i = edRed; i < edRed + 5; i++)
                cbLasDirect->Items->Add(DirType[i]);
        }
        else if(cbLasEffect->Text == EffectType[etBlinkReverse])
        {
            for(int i = edRed; i < edRed + 6; i++)
                cbLasDirect->Items->Add(DirType[i]);
        }
        else if(cbLasEffect->Text == EffectType[etLineTest])
        {
            for(int i = edLeft; i < edLeft + 10; i++)
            {
                if((TGEffectType)i == edLeftUp || (TGEffectType)i == edUpDown || (TGEffectType)i == edLeftAdd)
                    continue;
                if((TGEffectType)i == edLeft || (TGEffectType)i == edDown || (TGEffectType)i == edRightDown)
                    cbLasDirect->Items->Add(DirType[i]);
            }
        }
        else if(cbLasEffect->Text == EffectType[etRandom])
        {
            for(int i = edOrder + 1; i < edOrder + 2; i++)
                cbLasDirect->Items->Add(DirType[i]);
        }
        else if(cbLasEffect->Text == EffectType[et3DShift])
            cbLasDirect->Items->Add(DirType[edLeft]);
        else
            cbLasDirect->Items->Add(LangFile.NoEffect);
        if(cbLasDirect->Items->IndexOf(asED) > -1)
            cbLasDirect->ItemIndex = cbLasDirect->Items->IndexOf(asED);
        else
            cbLasDirect->ItemIndex = 0;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::rbTextNoClick(TObject *Sender)
{
        // 긴급문구 구조체 저장 함수
        /*if(rbPageNo2->Checked)
            ProtocolPageNo = cbPageNo->ItemIndex + 1;
        else
            ProtocolPageNo = 0;
        if(rbNo1->Checked)
            ProtocolBlockNo = 0;
        else if(rbNo2->Checked)
            ProtocolBlockNo = 1;
        else if(rbNo3->Checked)
            ProtocolBlockNo = 2;*/
        ProtocolData.ProtocolDispControl[ProtocolPageNo][ProtocolBlockNo] = cbDispControl->ItemIndex;
        ProtocolData.ProtocolDispType[ProtocolPageNo][ProtocolBlockNo] = cbDispType->ItemIndex;
        ProtocolData.ProtocolCodeKind[ProtocolPageNo][ProtocolBlockNo] = cbCodeKind->ItemIndex;
        ProtocolData.ProtocolFontSize[ProtocolPageNo][ProtocolBlockNo] = cbFontSize->ItemIndex;
        ProtocolData.ProtocolFontKind[ProtocolPageNo][ProtocolBlockNo] = cbFontKind->ItemIndex;
        ZeroMemory(&ProtocolData.ProtocolEntEffect[ProtocolPageNo][ProtocolBlockNo], sizeof(ProtocolData.ProtocolEntEffect[0][0]));
        memcpy(&ProtocolData.ProtocolEntEffect[ProtocolPageNo][ProtocolBlockNo][0], AnsiString(cbEntEffect->Text).c_str(), AnsiString(cbEntEffect->Text).Length());
        ZeroMemory(&ProtocolData.ProtocolLasDirect[ProtocolPageNo][ProtocolBlockNo], sizeof(ProtocolData.ProtocolLasDirect[0][0]));
        memcpy(&ProtocolData.ProtocolLasDirect[ProtocolPageNo][ProtocolBlockNo][0], AnsiString(cbEntDirect->Text).c_str(), AnsiString(cbEntDirect->Text).Length());
        ZeroMemory(&ProtocolData.ProtocolLasEffect[ProtocolPageNo][ProtocolBlockNo], sizeof(ProtocolData.ProtocolLasEffect[0][0]));
        memcpy(&ProtocolData.ProtocolLasEffect[ProtocolPageNo][ProtocolBlockNo][0], AnsiString(cbLasEffect->Text).c_str(), AnsiString(cbLasEffect->Text).Length());
        ZeroMemory(&ProtocolData.ProtocolLasDirect[ProtocolPageNo][ProtocolBlockNo], sizeof(ProtocolData.ProtocolLasDirect[0][0]));
        memcpy(&ProtocolData.ProtocolLasDirect[ProtocolPageNo][ProtocolBlockNo][0], AnsiString(cbLasDirect->Text).c_str(), AnsiString(cbLasDirect->Text).Length());
        ProtocolData.ProtocolColorType[ProtocolPageNo][ProtocolBlockNo] = cbAssistEffect->ItemIndex;
        ZeroMemory(&ProtocolData.ProtocolSpeed[ProtocolPageNo][ProtocolBlockNo], sizeof(ProtocolData.ProtocolSpeed[0][0]));
        memcpy(&ProtocolData.ProtocolSpeed[ProtocolPageNo][ProtocolBlockNo][0], AnsiString(cbSpeed->Text).c_str(), AnsiString(cbSpeed->Text).Length());
        ZeroMemory(&ProtocolData.ProtocolDelay[ProtocolPageNo][ProtocolBlockNo], sizeof(ProtocolData.ProtocolDelay[0][0]));
        memcpy(&ProtocolData.ProtocolDelay[ProtocolPageNo][ProtocolBlockNo][0], AnsiString(cbDelay->Text).c_str(), AnsiString(cbDelay->Text).Length());
        ProtocolData.ProtocolStartXPos[ProtocolPageNo][ProtocolBlockNo] = cbStartXPos->ItemIndex;
        ProtocolData.ProtocolStartYPos[ProtocolPageNo][ProtocolBlockNo] = cbStartYPos->ItemIndex;
        ProtocolData.ProtocolEndXPos[ProtocolPageNo][ProtocolBlockNo] = cbEndXPos->ItemIndex;
        ProtocolData.ProtocolEndYPos[ProtocolPageNo][ProtocolBlockNo] = cbEndYPos->ItemIndex;
        ProtocolData.ProtocolBGImage[ProtocolPageNo][ProtocolBlockNo] = cbBGImage->ItemIndex;
        ZeroMemory(&ProtocolData.ProtocolColor[ProtocolPageNo][ProtocolBlockNo], sizeof(ProtocolData.ProtocolColor[0][0]));
        memcpy(&ProtocolData.ProtocolColor[ProtocolPageNo][ProtocolBlockNo][0], AnsiString(eColor->Text).c_str(), AnsiString(eColor->Text).Length());
        ZeroMemory(&ProtocolData.ProtocolBGColor[ProtocolPageNo][ProtocolBlockNo], sizeof(ProtocolData.ProtocolBGColor[0][0]));
        memcpy(&ProtocolData.ProtocolBGColor[ProtocolPageNo][ProtocolBlockNo][0], AnsiString(eBGColor->Text).c_str(), AnsiString(eBGColor->Text).Length());
        ZeroMemory(&ProtocolData.ProtocolData[ProtocolPageNo][ProtocolBlockNo], sizeof(ProtocolData.ProtocolData[0][0]));
        memcpy(&ProtocolData.ProtocolData[ProtocolPageNo][ProtocolBlockNo][0], AnsiString(eData->Text).c_str(), AnsiString(eData->Text).Length());
}
//---------------------------------------------------------------------------


void __fastcall TFormMain::cbFontSizeClick(TObject *Sender)
{
        // 긴급문구 12x12 폰트는 배경색을 지정 못하게 막는다
        /*if(cbFontSize->ItemIndex == 0)
        {
            eBGColor->Enabled = false;
            Label18->Enabled = false;
        }
        else
        {
            eBGColor->Enabled = true;
            Label18->Enabled = true;
        }*/
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::rbTextNoRoadClick(TObject *Sender)
{
        // 긴급문구 구조체 불러오는 함수
        try{
            if(bFirst)
                rbTextNoClick(this);
            cbDispControl->Clear();
            cbDispControl->ItemIndex = -1;
            cbDispControl->Items->Add("OFF");
            cbDispControl->Items->Add("ON");
            if(rbNo1->Checked)
                ProtocolBlockNo = 0;
            else if(rbNo2->Checked)
                ProtocolBlockNo = 1;
            else if(rbNo3->Checked)
                ProtocolBlockNo = 2;
            if(rbPageNo2->Checked)
            {
                ProtocolPageNo = cbPageNo->ItemIndex + 1;
                ProtocolPageNo2 = ProtocolPageNo - 1;
                ProtocolBlockNo2 = ProtocolBlockNo;
            }
            else
            {
                ProtocolPageNo = 0;
                for(int i = 1; i <= 10; i++)
                {
                    cbDispControl->Items->Add(IntToStr(i));
                }
                cbDispControl->Items->Add("20");
                cbDispControl->Items->Add("30");
                cbDispControl->Items->Add("40");
                cbDispControl->Items->Add("50");
                cbDispControl->Items->Add("60");
                cbDispControl->Items->Add("70");
                cbDispControl->Items->Add("80");
                cbDispControl->Items->Add("90");
            }
            union{
                char cbSize;
                struct{
                    unsigned bit0 : 7;
                    unsigned bit1 : 1;
                }Bit;
            }uSize3;
            uSize3.cbSize = ProtocolData.ProtocolDispControl[ProtocolPageNo][ProtocolBlockNo];
            cbDispControl->ItemIndex = uSize3.Bit.bit0;
            cbDispType->ItemIndex = ProtocolData.ProtocolDispType[ProtocolPageNo][ProtocolBlockNo];
            cbCodeKind->ItemIndex = ProtocolData.ProtocolCodeKind[ProtocolPageNo][ProtocolBlockNo];
            cbFontSize->ItemIndex = ProtocolData.ProtocolFontSize[ProtocolPageNo][ProtocolBlockNo];
            cbFontKind->ItemIndex = ProtocolData.ProtocolFontKind[ProtocolPageNo][ProtocolBlockNo];
            cbFontSizeClick(this);
            cbEntEffect->ItemIndex = -1;
            cbEntEffect->ItemIndex = cbEntEffect->Items->IndexOf(AnsiString((char*)ProtocolData.ProtocolEntEffect[ProtocolPageNo][ProtocolBlockNo]).SubString(1, sizeof(ProtocolData.ProtocolEntEffect[ProtocolPageNo][ProtocolPageNo])));
            if(cbEntEffect->ItemIndex < 0)
                cbEntEffect->ItemIndex = 0;
            cbEntDirectDropDown(this);
            cbEntDirect->ItemIndex = cbEntDirect->Items->IndexOf(AnsiString((char*)ProtocolData.ProtocolEntDirect[ProtocolPageNo][ProtocolBlockNo]).SubString(1, sizeof(ProtocolData.ProtocolEntDirect[ProtocolPageNo][ProtocolPageNo])));
            if(cbEntDirect->ItemIndex < 0)
                cbEntDirect->ItemIndex = 0;
            cbLasEffect->ItemIndex = - 1;
            cbLasEffect->ItemIndex = cbLasEffect->Items->IndexOf(AnsiString((char*)ProtocolData.ProtocolLasEffect[ProtocolPageNo][ProtocolBlockNo]).SubString(1, sizeof(ProtocolData.ProtocolLasEffect[ProtocolPageNo][ProtocolPageNo])));
            if(cbLasEffect->ItemIndex < 0)
                cbLasEffect->ItemIndex = 1;
            cbLasDirectDropDown(this);
            cbLasDirect->ItemIndex = cbLasDirect->Items->IndexOf(AnsiString((char*)ProtocolData.ProtocolLasDirect[ProtocolPageNo][ProtocolBlockNo]).SubString(1, sizeof(ProtocolData.ProtocolLasDirect[ProtocolPageNo][ProtocolPageNo])));
            if(cbLasDirect->ItemIndex < 0)
                cbLasDirect->ItemIndex = 0;
            cbSpeed->ItemIndex = cbSpeed->Items->IndexOf(AnsiString((char*)ProtocolData.ProtocolSpeed[ProtocolPageNo][ProtocolBlockNo]).SubString(1, sizeof(ProtocolData.ProtocolSpeed[ProtocolPageNo][ProtocolPageNo])));
            cbDelay->ItemIndex = cbDelay->Items->IndexOf(AnsiString((char*)ProtocolData.ProtocolDelay[ProtocolPageNo][ProtocolBlockNo]).SubString(1, sizeof(ProtocolData.ProtocolDelay[ProtocolPageNo][ProtocolPageNo])));
            cbStartXPos->ItemIndex = ProtocolData.ProtocolStartXPos[ProtocolPageNo][ProtocolBlockNo];
            cbStartYPos->ItemIndex = ProtocolData.ProtocolStartYPos[ProtocolPageNo][ProtocolBlockNo];
            cbEndXPos->ItemIndex = ProtocolData.ProtocolEndXPos[ProtocolPageNo][ProtocolBlockNo];
            cbEndYPos->ItemIndex = ProtocolData.ProtocolEndYPos[ProtocolPageNo][ProtocolBlockNo];
            if(ProtocolBlockNo == 0)
            {
                cbBGImage->ItemIndex = ProtocolData.ProtocolBGImage[ProtocolPageNo][ProtocolBlockNo];
                cbBGImage->Enabled = true;
                cbDelay->Enabled = true;
            }
            else
            {
                cbBGImage->Enabled = false;
                if(bRealTimeIndex == 1)
                    cbDelay->Enabled = true;
                else
                    cbDelay->Enabled = false;
            }
            cbAssistEffect->ItemIndex = ProtocolData.ProtocolColorType[ProtocolPageNo][ProtocolBlockNo];
            eColor->Text = (WideString)AnsiString((char*)ProtocolData.ProtocolColor[ProtocolPageNo][ProtocolBlockNo]).TrimRight().SubString(1, sizeof(ProtocolData.ProtocolColor[ProtocolPageNo][ProtocolPageNo]));
            eBGColor->Text = (WideString)AnsiString((char*)ProtocolData.ProtocolBGColor[ProtocolPageNo][ProtocolBlockNo]).TrimRight().SubString(1, sizeof(ProtocolData.ProtocolBGColor[ProtocolPageNo][ProtocolPageNo]));
            AnsiString EditFileName = BasePath + IncludeTrailingPathDelimiter(FormMain->DestSysPath)
                + "EnvData" + IntToStr(ProtocolPageNo) + IntToStr(ProtocolBlockNo) + ".dat";
            eData->Text = (WideString)"";
            if(FileExists(EditFileName))
                eData->Lines->LoadFromFile(EditFileName);
            else
                eData->Text = (WideString)AnsiString((char*)ProtocolData.ProtocolData[ProtocolPageNo][ProtocolBlockNo]).SubString(1, sizeof(ProtocolData.ProtocolBGColor[ProtocolPageNo][ProtocolPageNo]));
        }catch(...){
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::ButtonEnabled(bool bEnabled)
{
        // 버튼 활성화 함수
        BtnSignSize->Enabled = bEnabled;
        BitBtnPowerLEDOn->Enabled = bEnabled;
        BitBtnPowerLEDOff->Enabled = bEnabled;
        BtnSendSync->Enabled = bEnabled;
        BtnMemInput->Enabled = bEnabled;
        BtnMsgMemClear->Enabled = bEnabled;
        BtnBGImage->Enabled = bEnabled;
        BtnReset->Enabled = bEnabled;
        BtnFReset->Enabled = bEnabled;
        BtnBright->Enabled = bEnabled;
        BtnTimeRead->Enabled = bEnabled;
        BtnTimeSet->Enabled = bEnabled;
        BtnScreen->Enabled = bEnabled;
        this->Enabled = bEnabled;
        this->BringToFront();
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::tsShow(TObject *Sender)
{
        if(ProtocolTabKind == 0)
            pnDataSend->Visible = true;
        else
            pnDataSend->Visible = false;
        /*if(FormMain->ComKind == 0)
        {
            cbDIBDAddress->Visible = pnDataSend->Visible;
            laAddress->Visible = pnDataSend->Visible;
        }
        else
        {
            cbDIBDAddress->Visible = false;
            laAddress->Visible = false;
        }*/
}
//---------------------------------------------------------------------------
                                                  
void __fastcall TFormMain::DataSendClick(TObject *Sender)
{
        // 문구 전송 버튼
        DataSend->Enabled = false;
        int tempType = PorotocolType;
        PorotocolType = 0;
        bStopSend = false;
        union{
            char cbSize;
            struct{
                unsigned bit0 : 4;
                unsigned bit1 : 4;
            }Bit;
        }uSize2;
        union{
            char cbSize;
            struct{
                unsigned bit0 : 3;
                unsigned bit1 : 1;
                unsigned bit2 : 3;
                unsigned bit3 : 1;
            }Bit;
        }uSize5;
        BYTE *SendProtocoData = NULL;
        try{
            bool bPortOpen = false;
            bool bConnect = false;
            int TextSize = 0;
            int Addr = 0;
            AnsiString Data = "";
            int ModuleSize = 0;
            ModuleSize = ModuleWidth * 2;
            int SendSize = 0;
            int TotDataSize = 0;
            AnsiString asMessage = "";
            AnsiString EntData = "";
            BYTE *RealPPAData;
            OPCode tempOPCode;
            AnsiString asSpeed = "";
            union{
                char cbSize;
                struct{
                    unsigned bit0 : 7;
                    unsigned bit1 : 1;
                }Bit;
            }uSize;
            int iCurBlockNo = 0;
            int iCurPageNo = 0;
            if(rbNo1->Checked)
                ProtocolBlockNo = 0;
            else if(rbNo2->Checked)
                ProtocolBlockNo = 1;
            else if(rbNo3->Checked)
                ProtocolBlockNo = 2;
            iCurBlockNo = ProtocolBlockNo;
            iCurPageNo = 0;
            ProtocolPageNo = 0;
            WideString wtempData;
            AnsiString tempData;
            AnsiString EditFileName;
            // 선택한 긴급문구 구조체에 저장
            if(rbPageNo2->Checked)
                ProtocolPageNo = cbPageNo->ItemIndex + 1;
            iCurPageNo = ProtocolPageNo;
            //if(cbDispControl->ItemIndex == 0 || cbDispControl->ItemIndex == 1)
                ProtocolData.ProtocolDispControl[iCurPageNo][iCurBlockNo] = cbDispControl->ItemIndex;
            //else
            //    ProtocolData.ProtocolDispControl[iCurPageNo][iCurBlockNo] = StrToIntDef(cbDispControl->Text, 1);
            ProtocolData.ProtocolDispType[iCurPageNo][iCurBlockNo] = cbDispType->ItemIndex;
            ProtocolData.ProtocolCodeKind[iCurPageNo][iCurBlockNo] = cbCodeKind->ItemIndex;
            ProtocolData.ProtocolFontSize[iCurPageNo][iCurBlockNo] = cbFontSize->ItemIndex;
            ProtocolData.ProtocolFontKind[iCurPageNo][iCurBlockNo] = cbFontKind->ItemIndex;
            tempData = cbEntEffect->Text;
            ZeroMemory(&ProtocolData.ProtocolEntEffect[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolEntEffect[0][0]));
            memcpy(&ProtocolData.ProtocolEntEffect[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
            tempData = cbEntDirect->Text;
            ZeroMemory(&ProtocolData.ProtocolEntDirect[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolEntDirect[0][0]));
            memcpy(&ProtocolData.ProtocolEntDirect[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
            tempData = cbLasEffect->Text;
            ZeroMemory(&ProtocolData.ProtocolLasEffect[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolLasEffect[0][0]));
            memcpy(&ProtocolData.ProtocolLasEffect[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
            tempData = cbLasDirect->Text;
            ZeroMemory(&ProtocolData.ProtocolLasDirect[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolLasDirect[0][0]));
            memcpy(&ProtocolData.ProtocolLasDirect[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
            ProtocolData.ProtocolColorType[iCurPageNo][iCurBlockNo] = cbAssistEffect->ItemIndex;
            tempData = cbSpeed->Text;
            ZeroMemory(&ProtocolData.ProtocolSpeed[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolSpeed[0][0]));
            memcpy(&ProtocolData.ProtocolSpeed[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
            tempData = cbDelay->Text;
            ZeroMemory(&ProtocolData.ProtocolDelay[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolDelay[0][0]));
            memcpy(&ProtocolData.ProtocolDelay[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
            ProtocolData.ProtocolStartXPos[iCurPageNo][iCurBlockNo] = cbStartXPos->ItemIndex;
            ProtocolData.ProtocolStartYPos[iCurPageNo][iCurBlockNo] = cbStartYPos->ItemIndex;
            ProtocolData.ProtocolEndXPos[iCurPageNo][iCurBlockNo] = cbEndXPos->ItemIndex;
            ProtocolData.ProtocolEndYPos[iCurPageNo][iCurBlockNo] = cbEndYPos->ItemIndex;
            ProtocolData.ProtocolBGImage[iCurPageNo][iCurBlockNo] = cbBGImage->ItemIndex;
            tempData = eColor->Text;
            ZeroMemory(&ProtocolData.ProtocolColor[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolColor[0][0]));
            memcpy(&ProtocolData.ProtocolColor[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
            tempData = eBGColor->Text;
            ZeroMemory(&ProtocolData.ProtocolBGColor[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolBGColor[0][0]));
            memcpy(&ProtocolData.ProtocolBGColor[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
            tempData = eData->Text;
            wtempData = eData->Text;
            ZeroMemory(&ProtocolData.ProtocolData[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolData[0][0]));
            memcpy(&ProtocolData.ProtocolData[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
            EditFileName = BasePath + IncludeTrailingPathDelimiter(DestSysPath)
                + "EnvData" + IntToStr(iCurPageNo) + IntToStr(iCurBlockNo) + ".dat";
            eData->Lines->SaveToFile(EditFileName);
            rbTextNoClick(this);
            WideString wtempData2 = "";
            int Totlen = 0;
            if(cbCodeKind->ItemIndex == 1)
                Totlen = wtempData.Length() * 2;
            else
                Totlen = tempData.Length();
            TextSize = Totlen;
            AnsiString SparePPAData = "";
            RealPPAData = new BYTE[Totlen];
            ZeroMemory(RealPPAData, Totlen);
            memcpy(&RealPPAData[0], tempData.c_str(), tempData.Length());
            BYTE *UniData = new BYTE[Totlen];
            FillMemory(UniData, Totlen, cbCodeKind->ItemIndex);
            int bytePos = 0;
            char casData[2];
            int tempSize = 0;
            int ppaPos = 0;
            int iRealSize = 0;
            BYTE tempByte[2];
            try{
                int Datalen = 0;
                BYTE tempChar;
                AnsiString asDummy = "";
                if(tempData.Pos("^[")) // 사용자 폰트 코드 사용 판단
                {
                    bytePos = 0;
                    for(int i = 0; i < Totlen; i++)
                    {
                        if(tempData.Pos("^["))
                        {
                            bytePos = 0;
                            tempSize = tempData.Pos("^[");
                            ppaPos = tempData.Pos("^]");
                            SparePPAData = tempData.SubString(tempSize + 2, ppaPos - (tempSize + 2));
                            tempData = tempData.Delete(tempSize, (ppaPos - tempSize) + 2);
                            TextSize = TextSize - ((ppaPos - tempSize) + 2);
                            Datalen = SparePPAData.Length();
                            for(int j = 0; j < Datalen; j++)
                            {
                                if(SparePPAData.Pos(";"))
                                    tempChar = StrToInt("0x" + SparePPAData.SubString(1, SparePPAData.Pos(";") - 1));
                                else
                                {
                                    tempChar = StrToInt("0x" + SparePPAData.SubString(1, SparePPAData.Length()));
                                    RealPPAData[tempSize - 1 + bytePos] = tempChar;
                                    UniData[tempSize - 1 + bytePos] = 0;
                                    TextSize++;
                                    bytePos++;
                                    break;
                                }
                                RealPPAData[tempSize - 1 + bytePos] = tempChar;
                                UniData[tempSize - 1 + bytePos] = 0;
                                SparePPAData = SparePPAData.Delete(1, SparePPAData.Pos(";"));
                                TextSize++;
                                bytePos++;
                            }
                            for(int j = 0; j < bytePos;j++)
                                asDummy += " ";
                            tempData = tempData.Insert(asDummy, tempSize);
                            memcpy(&RealPPAData[tempSize - 1 + bytePos], &RealPPAData[ppaPos + 1], Totlen - (ppaPos + 1));
                            asDummy = "";
                        }
                        else
                            break;
                    }
                    if(cbCodeKind->ItemIndex == 1) // 완성형코드를 유니코드로 변환
                    {
                        for(int i = 0; i < TextSize; i++)
                        {
                            if(UniData[i] == 0x01 && RealPPAData[i] != 0x00)
                            {
                                ZeroMemory(tempByte, sizeof(tempByte));
                                ZeroMemory(casData, sizeof(casData));
                                BYTE *tempRealData = new BYTE[Totlen];
                                ZeroMemory(tempRealData, Totlen);
                                if(RealPPAData[i] >= 0x80)
                                {
                                    casData[0] = RealPPAData[i];
                                    casData[1] = RealPPAData[i + 1];
                                    wtempData2 = AnsiString((char*)casData).SubString(1, 2);
                                    memcpy(tempByte, wtempData2.c_bstr(), 2);
                                    RealPPAData[i] = tempByte[1];
                                    RealPPAData[i + 1] = tempByte[0];
                                }
                                else
                                {
                                    memcpy(tempRealData, &RealPPAData[i + 1], Totlen - (i + 1));
                                    memcpy(&RealPPAData[i + 2], tempRealData, Totlen - (i + 2));
                                    RealPPAData[i + 1] = RealPPAData[i];
                                    RealPPAData[i] = 0x00;
                                    TextSize++;
                                    ZeroMemory(tempRealData, Totlen);
                                    memcpy(tempRealData, &UniData[i + 1], Totlen - (i + 1));
                                    memcpy(&UniData[i + 2], tempRealData, Totlen - (i + 2));
                                }
                                i++;
                                if(tempRealData)
                                {
                                    delete [] tempRealData;
                                    tempRealData = NULL;
                                }
                            }
                        }
                        for(int i = 0; i < TextSize; i+=2)
                        {
                            if(RealPPAData[i] == 0x00 && RealPPAData[i + 1] == 0x00)
                            {
                                TextSize = i;
                                break;
                            }
                        }
                    }
                }
                else
                {
                    if(cbCodeKind->ItemIndex == 1) // 완성형코드를 유니코드로 변환
                    {
                        for(int i = 0; i < wtempData.Length(); i++)
                        {
                            ZeroMemory(tempByte, sizeof(tempByte));
                            ZeroMemory(casData, sizeof(casData));
                            wtempData2 = wtempData.SubString(i + 1, 1);
                            WideCharToMultiByte(949, 0, wtempData2, -1, casData, sizeof(casData), NULL, NULL);
                            memcpy(tempByte, wtempData2.c_bstr(), 2);
                            if(Lang == 1)
                                RealPPAData[bytePos] = 0x00;
                            else
                                RealPPAData[bytePos] = tempByte[1];
                            RealPPAData[bytePos + 1] = tempByte[0];
                            bytePos += 2;
                        }
                    }
                }
            }catch(...){
                memcpy(&RealPPAData[0], AnsiString(eData->Text).TrimRight().c_str(), Totlen);
                TextSize = Totlen;
            }
            SendSize = TextSize;
            TotDataSize = SendSize * 2 + 16;
            if(SendProtocoData)
            {
                delete [] SendProtocoData;
                SendProtocoData = NULL;
            }
            SendProtocoData = new BYTE[TotDataSize];
            ZeroMemory(SendProtocoData, TotDataSize);
            // 전송 할 프로토콜로 메모리에 저장
            if(rbPageNo2->Checked)
                ProtocolPageNo = cbPageNo->ItemIndex + 1;
            else
                ProtocolPageNo = 0x00;
            SendProtocoData[0] = ProtocolPageNo;
            SendProtocoData[1] = iCurBlockNo;
            union{
                char cbSize;
                struct{
                    unsigned bit0 : 7;
                    unsigned bit1 : 1;
                }Bit;
            }uSize3;
            uSize3.cbSize = ProtocolData.ProtocolDispControl[iCurPageNo][iCurBlockNo];
            if(uSize3.Bit.bit0 == 0)
                SendProtocoData[2] = 0;
            else if(uSize3.Bit.bit0 == 1)
                SendProtocoData[2] = 99;
            else
                SendProtocoData[2] = uSize3.Bit.bit0 - 1;
            if(uSize3.Bit.bit1 == 1)
                SendProtocoData[2] |= 0x80;
            SendProtocoData[3] = ProtocolData.ProtocolDispType[iCurPageNo][iCurBlockNo];
            SendProtocoData[4] = ProtocolData.ProtocolCodeKind[iCurPageNo][iCurBlockNo];
            //if(ProtocolData.ProtocolFontSize[iCurPageNo][iCurBlockNo] == 0)
            //    SendProtocoData[5] = 1;
            //else
                SendProtocoData[5] = ProtocolData.ProtocolFontSize[iCurPageNo][iCurBlockNo] + 3;
            SendProtocoData[6] = InputSchEffect((TGEffectType)FindItemIndex(AnsiString((char*)ProtocolData.ProtocolEntEffect[iCurPageNo][iCurBlockNo]).SubString(1, sizeof(ProtocolData.ProtocolEntEffect[0][iCurBlockNo])), 2),
                (TGEffectDirection)FindItemIndex(AnsiString((char*)ProtocolData.ProtocolEntDirect[iCurPageNo][iCurBlockNo]).SubString(1, sizeof(ProtocolData.ProtocolEntDirect[0][iCurBlockNo])), 3));
            SendProtocoData[7] = InputSchEffect((TGEffectType)FindItemIndex(AnsiString((char*)ProtocolData.ProtocolLasEffect[iCurPageNo][iCurBlockNo]).SubString(1, sizeof(ProtocolData.ProtocolLasEffect[0][iCurBlockNo])), 2),
                (TGEffectDirection)FindItemIndex(AnsiString((char*)ProtocolData.ProtocolLasDirect[iCurPageNo][iCurBlockNo]).SubString(1, sizeof(ProtocolData.ProtocolLasDirect[0][iCurBlockNo])), 3));
            SendProtocoData[8] = ProtocolData.ProtocolColorType[iCurPageNo][iCurBlockNo];
            asSpeed = AnsiString((char*)ProtocolData.ProtocolSpeed[iCurPageNo][iCurBlockNo]).SubString(1, sizeof(ProtocolData.ProtocolSpeed[0][iCurBlockNo])).TrimRight();
            if(!asSpeed.SubString(asSpeed.Pos("(") + 1, AnsiString(LangFile.Message01).Length()).AnsiCompareIC(LangFile.Message01))
                SendProtocoData[9] = 0;
            else if(!asSpeed.SubString(asSpeed.Pos("(") + 1, AnsiString(LangFile.Message02).Length()).AnsiCompareIC(LangFile.Message02))
                SendProtocoData[9] = 255;
            else
                SendProtocoData[9] = StrToIntDef(asSpeed.TrimRight(), 20);
            asSpeed = AnsiString((char*)ProtocolData.ProtocolDelay[iCurPageNo][iCurBlockNo]).SubString(1, sizeof(ProtocolData.ProtocolDelay[0][iCurBlockNo])).TrimRight();
            if(asSpeed.Pos("("))
            {
                SendProtocoData[10] = SetDelayTime(StrToIntDef(asSpeed.SubString(1, asSpeed.Pos("(") - 1).Trim(), 2));
                //if(SendProtocoData[10] == 0xf0)
                //    SendProtocoData[10] = 0x78;
            }
            else
                SendProtocoData[10] = SetDelayTime(StrToIntDef(asSpeed, 2)) / 2;
            SendProtocoData[11] = ProtocolData.ProtocolStartXPos[iCurPageNo][iCurBlockNo];
            SendProtocoData[12] = ProtocolData.ProtocolStartYPos[iCurPageNo][iCurBlockNo];
            SendProtocoData[13] = ProtocolData.ProtocolEndXPos[iCurPageNo][iCurBlockNo];
            SendProtocoData[14] = ProtocolData.ProtocolEndYPos[iCurPageNo][iCurBlockNo];
            if(ProtocolData.ProtocolBGImage[iCurPageNo][iCurBlockNo] >= 11)
                SendProtocoData[15] = ProtocolData.ProtocolBGImage[iCurPageNo][iCurBlockNo] + 190;
            else
                SendProtocoData[15] = ProtocolData.ProtocolBGImage[iCurPageNo][iCurBlockNo];
            int ColorSize = AnsiString((char*)ProtocolData.ProtocolColor[iCurPageNo][iCurBlockNo]).SubString(1, sizeof(ProtocolData.ProtocolColor[0][iCurBlockNo])).TrimRight().Length();
            if(SendSize < ColorSize)
                ColorSize = SendSize;
            BYTE tempColor = Hex2ByteConvert(AnsiString((char*)ProtocolData.ProtocolColor[iCurPageNo][iCurBlockNo]).SubString(ColorSize, 1));
            FillMemory(&SendProtocoData[16], SendSize, tempColor);
            bool bASCII = true;
            int iHan = 0;
            int iCodeCnt = 0;
            for(int i = 1; i <= ColorSize; i++)
            {
                tempColor = Hex2ByteConvert(AnsiString((char*)ProtocolData.ProtocolColor[iCurPageNo][iCurBlockNo]).SubString(i, 1));
                SendProtocoData[16 + iCodeCnt] = tempColor;
                if((RealPPAData[iCodeCnt++] >= 0x80 && Lang != 1) || cbCodeKind->ItemIndex == 1)
                    SendProtocoData[16 + iCodeCnt++] = 0x00;
                if(iCodeCnt >= SendSize)
                    break;
            }
            if(iCodeCnt <= SendSize)
            {
                for(; iCodeCnt < SendSize;)
                {
                    SendProtocoData[16 + iCodeCnt] = tempColor;
                    if((RealPPAData[iCodeCnt++] >= 0x80 && Lang != 1) || cbCodeKind->ItemIndex == 1)
                        SendProtocoData[16 + iCodeCnt++] = 0x00;
                }
            }
            if(eBGColor->Enabled)
            {
                int ColorSize2 = AnsiString((char*)ProtocolData.ProtocolBGColor[iCurPageNo][iCurBlockNo]).SubString(1, sizeof(ProtocolData.ProtocolBGColor[0][iCurBlockNo])).TrimRight().Length();
                BYTE tempColor2 = Hex2ByteConvert(AnsiString((char*)ProtocolData.ProtocolBGColor[iCurPageNo][iCurBlockNo]).SubString(ColorSize, 1));
                if((int)tempColor2 > 15)
                    tempColor2 = 0;
                if(SendSize < ColorSize2)
                    ColorSize2 = SendSize;
                iCodeCnt = 0;
                for(int i = 1; i <= ColorSize2; i++)
                {
                    tempColor2 = Hex2ByteConvert(AnsiString((char*)ProtocolData.ProtocolBGColor[iCurPageNo][iCurBlockNo]).SubString(i, 1));
                    if((int)tempColor2 > 15)
                        tempColor2 = 0;
                    if(cbFontKind->ItemIndex == 0)
                    {
                        uSize2.cbSize = SendProtocoData[16 + iCodeCnt];
                        uSize2.Bit.bit1 = tempColor2;
                        SendProtocoData[16 + iCodeCnt] = uSize2.cbSize;
                    }
                    else
                    {
                        uSize5.cbSize = SendProtocoData[16 + iCodeCnt];
                        uSize5.Bit.bit2 = tempColor2;
                        if(cbFontKind->ItemIndex >= 2)
                        {
                            uSize5.Bit.bit3 = 1;
                            if(cbFontKind->ItemIndex == 3)
                                uSize5.Bit.bit1 = 1;
                            else
                                uSize5.Bit.bit1 = 0;
                        }
                        else
                            uSize5.Bit.bit1 = cbFontKind->ItemIndex;
                        SendProtocoData[16 + iCodeCnt] = uSize5.cbSize;
                    }
                    if((RealPPAData[iCodeCnt++] >= 0x80 && Lang != 1) || cbCodeKind->ItemIndex == 1)
                        SendProtocoData[16 + iCodeCnt++] = 0x00;
                    if(iCodeCnt >= SendSize)
                        break;
                }
                if(iCodeCnt <= SendSize)
                {
                    for(; iCodeCnt < SendSize;)
                    {
                        if(cbFontKind->ItemIndex == 0)
                        {
                            uSize2.cbSize = SendProtocoData[16 + iCodeCnt];
                            uSize2.Bit.bit1 = tempColor2;
                            SendProtocoData[16 + iCodeCnt] = uSize2.cbSize;
                        }
                        else
                        {
                            uSize5.cbSize = SendProtocoData[16 + iCodeCnt];
                            uSize5.Bit.bit2 = tempColor2;
                            if(cbFontKind->ItemIndex >= 2)
                            {
                                uSize5.Bit.bit3 = 1;
                                if(cbFontKind->ItemIndex == 3)
                                    uSize5.Bit.bit1 = 1;
                                else
                                    uSize5.Bit.bit1 = 0;
                            }
                            else
                                uSize5.Bit.bit1 = cbFontKind->ItemIndex;
                            SendProtocoData[16 + iCodeCnt] = uSize5.cbSize;
                        }
                        if((RealPPAData[iCodeCnt++] >= 0x80 && Lang != 1) || cbCodeKind->ItemIndex == 1)
                            SendProtocoData[16 + iCodeCnt++] = 0x00;
                    }
                }
            }
            asMessage = LangFile.FormProtocolTab1;
            tempOPCode = FED;
            FillMemory(&SendProtocoData[16 + SendSize], SendSize, 0x20);
            memcpy(&SendProtocoData[16 + SendSize], RealPPAData, TextSize);
            if(RealPPAData)
            {
                delete [] RealPPAData;
                RealPPAData = NULL;
            }
            if(UniData)
            {
                delete [] UniData;
                UniData = NULL;
            }
            // 저장된 메모리 값을 전광판에 전송
            Addr = Controler1;
            if(!bPortOpen)
                bConnect = ComPortSet(ComKind, PortUse1, BaudRateUse1, Disable, Disable, IPAddress, IPPort, false);
            if(bConnect)
            {
                if(ModuleData(ComKind, Addr, SendProtocoData, TotDataSize, 0, tempOPCode, bMsg))
                    AddLog(asMessage + " [OK]" + asLogMessage, clBlack);
                else
                    AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
            }
            if(SendProtocoData)
            {
                delete [] SendProtocoData;
                SendProtocoData = NULL;
            }
            ComClose(ComKind);
        }catch(...){
        }
        DataSend->Enabled = true;
        PorotocolType = tempType;
        this->BringToFront();
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnSignSizeClick(TObject *Sender)
{
        // 전광판 크기 전송 버튼
        bStopSend = false;
        BYTE SendData[13];
        ZeroMemory(SendData, sizeof(SendData));
        if(!FSetting)
        {
            if(cbProgramType->ItemIndex == 0)
                ProgramType = e2BPP;
            else if(cbProgramType->ItemIndex == 2)
                ProgramType = e8BPP;
            else if(cbProgramType->ItemIndex == 3)
                ProgramType = e24BPP;
            //else if(cbProgramType->ItemIndex == 3)
            //    FormMain->Setup.ProgramType = e16BPP;
            else //if(cbProgramType->ItemIndex == 3)
                ProgramType = e3BPP;
            ModuleHeight = UpDownHeight->Position;
            ModuleWidth = UpDownWidth->Position;
            Direct = cbDirection->ItemIndex;
        }
        SendData[0] = ProgramType;
        SendData[1] = ModuleHeight;
        SendData[2] = ModuleWidth;
        SendData[3] = Direct;
        SendData[4] = 0;
        SendData[5] = 0xF1;
        int iSendSize = 6;//3 + cbDirectCheck->Checked;
        bool bConnected = false;
        ButtonEnabled(false);
        int Addr = 0;
	if(bRS485)
            Addr = Controler1;
        if(ComPortSet(ComKind, PortUse1, BaudRateUse1, Disable, Disable, IPAddress, IPPort, false))
        {
            if(PorotocolType == 1)
            {
                AnsiString asSendData = "![" + IntTo485Address(Addr) + "040";
                char Buf[3];
                ZeroMemory(Buf, sizeof(Buf));
                wsprintf(Buf, "%02d", SendData[1]);
                asSendData = asSendData + AnsiString(Buf);
                ZeroMemory(Buf, sizeof(Buf));
                wsprintf(Buf, "%02d", SendData[2]);
                asSendData = asSendData + AnsiString(Buf);
                ZeroMemory(Buf, sizeof(Buf));
                wsprintf(Buf, "%01d", SendData[3]);
                asSendData = asSendData + AnsiString(Buf) + "!]";
                iSendSize = asSendData.Length();
                memcpy(SendData, asSendData.c_str(), iSendSize);
                bConnected = ModuleASCIIData(ComKind, Addr, SendData, iSendSize, bMsg);
                if(bConnected)
                {
                    if(bDataFail)
                        AddLog(LangFile.Message07 + " (" + IntToStr(ModuleHeight) + " X " + IntToStr(ModuleWidth) + ") [FAIL]" + asLogMessage, clRed);
                    else
                        AddLog(LangFile.Message07 + " (" + IntToStr(ModuleHeight) + " X " + IntToStr(ModuleWidth) + ") [OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(LangFile.Message07 + " (" + IntToStr(ModuleHeight) + " X " + IntToStr(ModuleWidth) + ") [FAIL]" + asLogMessage, clRed);
                if(bConnected)
                {
                    NoneDelayCount(500);
                    asSendData = "![" + IntTo485Address(Addr) + "081!]";
                    iSendSize = asSendData.Length();
                    memcpy(SendData, asSendData.c_str(), iSendSize);
                    if(ModuleASCIIData(ComKind, Addr, SendData, iSendSize, bMsg))
                    {
                        if(bDataFail)
                            AddLog("Information [FAIL]" + asLogMessage, clRed);
                        else
                            AddLog("Information [OK]" + asLogMessage, clBlack);
                    }
                    else
                        AddLog("Information  [FAIL]" + asLogMessage, clRed);
                }
            }
            else
            {
                if(ModuleData(ComKind, Addr, SendData, iSendSize, 0, BS, bMsg))
                {
                    if(bDataFail)
                        AddLog(LangFile.Message07 + " (" + IntToStr(ModuleHeight) + " X " + IntToStr(ModuleWidth) + ") [FAIL]" + asLogMessage, clRed);
                    else
                        AddLog(LangFile.Message07 + " (" + IntToStr(ModuleHeight) + " X " + IntToStr(ModuleWidth) + ") [OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(LangFile.Message07 + " (" + IntToStr(ModuleHeight) + " X " + IntToStr(ModuleWidth) + ") [FAIL]" + asLogMessage, clRed);
            }
            ComClose(ComKind);
        }
        int iSPos = cbStartXPos->ItemIndex;
        int iEPos = cbEndXPos->ItemIndex;
        cbStartXPos->ItemIndex = -1;
        cbEndXPos->ItemIndex = -1;
        cbStartXPos->Clear();
        cbEndXPos->Clear();
        for(int i = 0; i <= ModuleWidth * 4; i++)
        {
            cbStartXPos->Items->Add(IntToStr(i * 4));
            cbEndXPos->Items->Add(IntToStr(i * 4));
        }
        cbStartXPos->ItemIndex = iSPos;
        if(iEPos > cbEndXPos->Items->Count - 1)
            cbEndXPos->ItemIndex = cbEndXPos->Items->Count - 1;
        else
            cbEndXPos->ItemIndex = iEPos;

        iSPos = cbStartYPos->ItemIndex;
        iEPos = cbEndYPos->ItemIndex;
        cbStartYPos->ItemIndex = -1;
        cbEndYPos->ItemIndex = -1;
        cbStartYPos->Clear();
        cbEndYPos->Clear();
        for(int i = 0; i <= ModuleHeight * 4; i++)
        {
            cbStartYPos->Items->Add(IntToStr(i * 4));
            cbEndYPos->Items->Add(IntToStr(i * 4));
        }
        cbStartYPos->ItemIndex = iSPos;
        if(iEPos > cbEndYPos->Items->Count - 1)
            cbEndYPos->ItemIndex = cbEndYPos->Items->Count - 1;
        else
            cbEndYPos->ItemIndex = iEPos;
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------
                                                                        
void __fastcall TFormMain::sbLogClearClick(TObject *Sender)
{
        RichEditLog->Clear();
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::cbDIBDAddressClick(TObject *Sender)
{
        // 전공판 어드레스 값
        Controler1 = cbDIBDAddress->ItemIndex;
}
//---------------------------------------------------------------------------                                                                                        

void __fastcall TFormMain::rbPageNoClick(TObject *Sender)
{
        // 일반문구 메시지 번호
        rbTextNoClick(this);
        cbPageNo->Visible = rbPageNo2->Checked;
        rbTextNoRoadClick(this);
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::btnAddClick(TObject *Sender)
{
        // ASCII 문구 추가 버튼
        AnsiString asData = mASCIIText1->Text;
        if(lbMesageList->Items->Count <= 11)
        {
            lbMesageList->Items->Add(asData.SubString(1, 20));
            lbMesageList->ItemIndex = lbMesageList->Items->Count - 1;
            int iIndex = lbMesageList->ItemIndex;
            ZeroMemory(&ASCIIMessageData[iIndex].MessageData, sizeof(ASCIIMessageData[0].MessageData));
            if(asData.Length() > sizeof(ASCIIMessageData[0].MessageData))
                memcpy(&ASCIIMessageData[iIndex].MessageData, asData.c_str(), sizeof(ASCIIMessageData[0].MessageData));
            else
                memcpy(&ASCIIMessageData[iIndex].MessageData, asData.c_str(), asData.Length());
        }
        AnsiString ASCIIMessageFileName = BasePath + IncludeTrailingPathDelimiter(DestSysPath) + "ASCIIMessageData.dat";
        TFileStream *ASCIIMessageFile = NULL;
        try{
            ASCIIMessageFile = new TFileStream(ASCIIMessageFileName, fmCreate | fmShareCompat);
            ASCIIMessageFile->Write(&ASCIIMessageData, sizeof(ASCIIMessageData[0]) * lbMesageList->Items->Count);
        }catch(...){
        }
        if(ASCIIMessageFile)
        {
            delete ASCIIMessageFile;
            ASCIIMessageFile = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::btnDeleteClick(TObject *Sender)
{
        // ASCII 문구 삭제 버튼
        if(lbMesageList->Items->Count > 1)
        {
            int i = lbMesageList->ItemIndex;
            if(i == lbMesageList->Items->Count - 1)
            {
                ZeroMemory(&ASCIIMessageData[i], sizeof(ASCIIMessageData[0]));
            }
            while(i < lbMesageList->Items->Count)
            {
                ZeroMemory(&ASCIIMessageData[i], sizeof(ASCIIMessageData[0]));
                memcpy(&ASCIIMessageData[i], &ASCIIMessageData[i + 1], sizeof(ASCIIMessageData[0]));
                ZeroMemory(&ASCIIMessageData[i + 1], sizeof(ASCIIMessageData[0]));
                i++;
            }
            lbMesageList->Items->Delete(lbMesageList->Items->Count - 1);
        }
        lbMesageListClick(this);
        AnsiString asMessage = "";
        for(int i = 0; i < lbMesageList->Items->Count; i++)
        {
            asMessage = AnsiString((char *)ASCIIMessageData[i].MessageData).SubString(1, 20);
            lbMesageList->Items->Strings[i] = asMessage;
        }
        AnsiString ASCIIMessageFileName = BasePath + IncludeTrailingPathDelimiter(DestSysPath) + "ASCIIMessageData.dat";
        TFileStream *ASCIIMessageFile = NULL;
        try{
            ASCIIMessageFile = new TFileStream(ASCIIMessageFileName, fmCreate | fmShareCompat);
            ASCIIMessageFile->Write(&ASCIIMessageData, sizeof(ASCIIMessageData[0]) * lbMesageList->Items->Count);
        }catch(...){
        }
        if(ASCIIMessageFile)
        {
            delete ASCIIMessageFile;
            ASCIIMessageFile = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::btnSave1Click(TObject *Sender)
{
        // ASCII 문구 수정 버튼
        //AnsiString asData = mASCIIText->Text;
        //int iIndex = lbMesageList->ItemIndex;
        AnsiString asData = "";
        int iIndex = 0;
        TTntSpeedButton *BtnSave;
        TTntRichEdit *tMemo;
        AnsiString EditFileName = "";
        try{
            for(int i = 0; i < 10; i++)
            {
                BtnSave = dynamic_cast<TTntSpeedButton *>(FindComponent("btnSave" + IntToStr(i + 1)));
                if(BtnSave == Sender)
                {
                    iIndex = i;
                    tMemo = dynamic_cast<TTntRichEdit*>(FindComponent("mASCIIText" + IntToStr(i + 1)));
                    if(tMemo)
                    {
                        asData = tMemo->Text;
                        EditFileName = BasePath + IncludeTrailingPathDelimiter(DestSysPath) + "ASCIIData" + IntToStr(i + 1) + ".dat";
                        tMemo->Lines->SaveToFile(EditFileName);
                    }
                    break;
                }
            }
        }catch(...){
        }
        ZeroMemory(&ASCIIMessageData[iIndex].MessageData, sizeof(ASCIIMessageData[0].MessageData));
        if(asData.Length() > sizeof(ASCIIMessageData[0].MessageData))
            memcpy(&ASCIIMessageData[iIndex].MessageData, asData.c_str(), sizeof(ASCIIMessageData[0].MessageData));
        else
            memcpy(&ASCIIMessageData[iIndex].MessageData, asData.c_str(), asData.Length());
        asData = AnsiString((char *)ASCIIMessageData[iIndex].MessageData).SubString(1, 20);
        //lbMesageList->Items->Strings[iIndex] = asData;
        AnsiString ASCIIMessageFileName = BasePath + IncludeTrailingPathDelimiter(DestSysPath) + "ASCIIMessageData.dat";
        TFileStream *ASCIIMessageFile = NULL;
        try{
            ASCIIMessageFile = new TFileStream(ASCIIMessageFileName, fmCreate | fmShareCompat);
            //ASCIIMessageFile->Write(&ASCIIMessageData, sizeof(ASCIIMessageData[0]) * lbMesageList->Items->Count);
            ASCIIMessageFile->Write(&ASCIIMessageData, sizeof(ASCIIMessageData[0]) * 11);
        }catch(...){
        }
        if(ASCIIMessageFile)
        {
            delete ASCIIMessageFile;
            ASCIIMessageFile = NULL;
        }
        PreviewMode = iIndex + 1;
        PreviewSaveIniFile();
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::ASCIISend1Click(TObject *Sender)
{
        // ASCII 문구 전송 버튼
        bStopSend = false;
        int iIndex = 0;
        TTntSpeedButton *BtnSend;
        TTntRichEdit *tMemo;
        try{
            for(int i = 0; i < 10; i++)
            {
                BtnSend = dynamic_cast<TTntSpeedButton *>(FindComponent("ASCIISend" + IntToStr(i + 1)));
                if(BtnSend == Sender)
                {
                    iIndex = i;
                    BtnSend = dynamic_cast<TTntSpeedButton *>(FindComponent("btnSave" + IntToStr(i + 1)));
                    tMemo = dynamic_cast<TTntRichEdit*>(FindComponent("mASCIIText" + IntToStr(i + 1)));
                    if(BtnSend)
                        BtnSend->Click();
                    break;
                }
            }
        }catch(...){
        }
        BtnSend = dynamic_cast<TTntSpeedButton *>(FindComponent("ASCIISend" + IntToStr(iIndex + 1)));
        if(BtnSend)
            BtnSend->Enabled = false;
        bool bConnect = false;
        BYTE Addr = Controler1;
        AnsiString asMessage = LangFile.FormProtocolTab2;
        bConnect = ComPortSet(ComKind, PortUse1, BaudRateUse1, Disable, Disable, IPAddress, IPPort, false);
        bool bJohap = false;
        //bool tempPorotocolType = PorotocolType;
        //asJohap = "";
        if(bConnect)
        {
            //AnsiString asSendData = AnsiString(mASCIIText->Text).TrimRight();
            BYTE *RealPPAData;
            bool bUniCode = false;
            WideString wtempData;
            WideString wtempData2;
            AnsiString asSendData = tMemo->Text;//AnsiString((char*)ASCIIMessageData[iIndex].MessageData).SubString(1, sizeof(ASCIIMessageData[0].MessageData)).TrimRight();
            wtempData = tMemo->Text;
            int Totlen = 0;
            int bytePos = 0;
            bytePos = asSendData.Pos("/F01");
            if(ProtocolData.ProtocolCodeKind[11][ASCIIPageNo] == 1 || bytePos)
            {
                bytePos = asSendData.Pos("/F00");
                if(bytePos)
                    Totlen = asSendData.Length();
                else
                {
                    bUniCode = true;
                    //PorotocolType = 1;
                    Totlen = wtempData.Length() * 2;
                }
            }
            else
                Totlen = asSendData.Length();
            bytePos = 0;
            RealPPAData = new BYTE[Totlen];
            ZeroMemory(RealPPAData, Totlen);
            int textCnt = 0;
            if(bUniCode)
            {
                char casData[2];
                BYTE tempByte[2];
                AnsiString asChar = "";
                asLogMessage = " ";
                for(int i = 0; i < wtempData.Length(); i++)
                {
                    //ZeroMemory(casData, sizeof(casData));
                    wtempData2 = wtempData.SubString(i + 1, 1);
                    //WideCharToMultiByte(949, 0, wtempData2, -1, casData, sizeof(casData), NULL, NULL);
                    ZeroMemory(tempByte, sizeof(tempByte));
                    memcpy(tempByte, wtempData2.c_bstr(), 2);
                    //if((BYTE)casData[0] >= 128)
                    if(i >= 0 && i <= 4)
                    {
                        for(int j = 0; j < 5; j++)
                        {
                            wtempData2 = wtempData.SubString(i + j + 1, 1);
                            ZeroMemory(tempByte, sizeof(tempByte));
                            memcpy(tempByte, wtempData2.c_bstr(), 2);
                            RealPPAData[bytePos + j] = tempByte[0];
                        }
                        bytePos += 5;
                        asLogMessage = asLogMessage + wtempData.SubString(1, 5);
                        i += 4;
                    }
                    else if(tempByte[0] == '!')
                    {
                        RealPPAData[bytePos] = tempByte[0];
                        wtempData2 = wtempData.SubString(i + 2, 1);
                        ZeroMemory(tempByte, sizeof(tempByte));
                        memcpy(tempByte, wtempData2.c_bstr(), 2);
                        if(tempByte[0] == ']')
                        {
                            RealPPAData[bytePos + 1] = tempByte[0];
                            bytePos += 2;
                            asLogMessage = asLogMessage + wtempData.SubString(i + 1, 2);
                            i++;
                        }
                        else
                        {
                            RealPPAData[bytePos + 1] = RealPPAData[bytePos];
                            RealPPAData[bytePos] = 0x00;
                            asLogMessage = asLogMessage + " " + Byte2HexConvert(RealPPAData[bytePos]) + Byte2HexConvert(RealPPAData[bytePos + 1]);
                            bytePos += 2;
                        }
                    }
                    /*else if(tempByte[0] == ']')
                    {
                        RealPPAData[bytePos] = tempByte[0];
                        wtempData2 = wtempData.SubString(i + 2, 1);
                        ZeroMemory(tempByte, sizeof(tempByte));
                        memcpy(tempByte, wtempData2.c_bstr(), 2);
                        RealPPAData[bytePos + 1] = RealPPAData[bytePos];
                        RealPPAData[bytePos] = 0x00;
                        bytePos += 2;
                        if(tempByte[0] == ']')
                            i++;
                    }*/
                    else if(tempByte[0] == '/')
                    {
                        RealPPAData[bytePos] = tempByte[0];
                        wtempData2 = wtempData.SubString(i + 2, 1);
                        ZeroMemory(tempByte, sizeof(tempByte));
                        memcpy(tempByte, wtempData2.c_bstr(), 2);
                        asChar = AnsiString((char)tempByte[0]).LowerCase();
                        if(asChar == "p" || asChar == "d" || asChar == "f" || asChar == "e" || asChar == "s" || asChar == "x" || asChar == "y")
                        {
                            bytePos++;
                            RealPPAData[bytePos] = tempByte[0];
                            for(int j = 0; j < 4; j++)
                            {
                                wtempData2 = wtempData.SubString(i + j + 3, 1);
                                ZeroMemory(tempByte, sizeof(tempByte));
                                memcpy(tempByte, wtempData2.c_bstr(), 2);
                                RealPPAData[bytePos + j + 1] = tempByte[0];
                            }
                            bytePos += 5;
                            asLogMessage = asLogMessage + wtempData.SubString(i + 1, 6);
                            i += 5;
                        }
                        else if(asChar == "b" || asChar == "u")
                        {
                            bytePos++;
                            RealPPAData[bytePos] = tempByte[0];
                            for(int j = 0; j < 3; j++)
                            {
                                wtempData2 = wtempData.SubString(i + j + 3, 1);
                                ZeroMemory(tempByte, sizeof(tempByte));
                                memcpy(tempByte, wtempData2.c_bstr(), 2);
                                RealPPAData[bytePos + j + 1] = tempByte[0];
                            }
                            bytePos += 4;
                            asLogMessage = asLogMessage + wtempData.SubString(i + 1, 5);
                            i += 4;
                        }
                        else if(asChar == "i")
                        {
                            bytePos++;
                            RealPPAData[bytePos] = tempByte[0];
                            for(int j = 0; j < 2; j++)
                            {
                                wtempData2 = wtempData.SubString(i + j + 3, 1);
                                ZeroMemory(tempByte, sizeof(tempByte));
                                memcpy(tempByte, wtempData2.c_bstr(), 2);
                                RealPPAData[bytePos + j + 1] = tempByte[0];
                            }
                            bytePos += 3;
                            asLogMessage = asLogMessage + wtempData.SubString(i + 1, 4);
                            i += 3;
                        }
                        else if(asChar == "c" || asChar == "g" || asChar == "t")
                        {
                            bytePos++;
                            RealPPAData[bytePos] = tempByte[0];
                            wtempData2 = wtempData.SubString(i + 3, 1);
                            ZeroMemory(tempByte, sizeof(tempByte));
                            memcpy(tempByte, wtempData2.c_bstr(), 2);
                            bytePos++;
                            RealPPAData[bytePos] = tempByte[0];
                            bytePos++;
                            asLogMessage = asLogMessage + wtempData.SubString(i + 1, 3);
                            i += 2;
                        }
                        else if(tempByte[0] == '/')
                        {
                            RealPPAData[bytePos + 1] = RealPPAData[bytePos];
                            RealPPAData[bytePos] = 0x00;
                            asLogMessage = asLogMessage + " " + Byte2HexConvert(RealPPAData[bytePos]) + Byte2HexConvert(RealPPAData[bytePos + 1]);
                            bytePos += 2;
                            i++;
                        }
                        else
                        {
                            RealPPAData[bytePos + 1] = RealPPAData[bytePos];
                            RealPPAData[bytePos] = 0x00;
                            asLogMessage = asLogMessage + " " + Byte2HexConvert(RealPPAData[bytePos]) + Byte2HexConvert(RealPPAData[bytePos + 1]);
                            bytePos += 2;
                        }
                    }
                    else
                    {
                        RealPPAData[bytePos] = tempByte[1];
                        RealPPAData[bytePos + 1] = tempByte[0];
                        asLogMessage = asLogMessage + " " + Byte2HexConvert(RealPPAData[bytePos]) + Byte2HexConvert(RealPPAData[bytePos + 1]);
                        bytePos += 2;
                    }
                }
            }
            else
            {
                /*char casData[2];
                AnsiString asData = "";
                for(int i = 0; i < wtempData.Length(); i++)
                {
                    ZeroMemory(casData, sizeof(casData));
                    wtempData2 = wtempData.SubString(i + 1, 1);
                    WideCharToMultiByte(949, 0, wtempData2, -1, casData, sizeof(casData), NULL, NULL);
                    if((BYTE)casData[0] >= 128)
                    {
                        asData = AnsiString(AnsiString((char*)casData).SubString(1, 2)).SubString(1, 2);
                        asData = HanConvert->Conv_W2J(asData).SubString(1, 2);
                        memcpy(casData, asData.c_str(), asData.Length());
                        RealPPAData[bytePos] = casData[1];
                        RealPPAData[bytePos + 1] = casData[0];
                        bytePos += 2;
                        bJohap = true;
                    }
                    else
                    {
                        RealPPAData[bytePos] = casData[0];
                        bytePos ++;
                    }
                }*/
                bytePos = asSendData.Length();
                memcpy(RealPPAData, asSendData.c_str(), bytePos);
                asLogMessage = asSendData;
            }
            //if(bJohap)
            //    asJohap = asSendData;
            if(ModuleASCIIData(ComKind, Addr, RealPPAData, bytePos, bMsg))
            {
                if(bDataFail)
                    AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
                else
                    AddLog(asMessage + " [OK]" + asLogMessage, clBlack);
            }
            else
            {
                //asLogMessage = asJohap;
                AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
            }
            ComClose(ComKind);
            if(RealPPAData)
            {
                delete [] RealPPAData;
                RealPPAData = NULL;
            }
        }
        if(BtnSend)
            BtnSend->Enabled = true;
        //PorotocolType = tempPorotocolType;
        PreviewMode = iIndex + 1;
        PreviewSaveIniFile();
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::lbMesageListClick(TObject *Sender)
{
        // ASCII 문구 클릭시 문구 내용 보여줌
        lbMesageList->Invalidate();
        int iIndex = lbMesageList->ItemIndex;
        if(iIndex >= 0)
        {
            AnsiString asData = AnsiString((char*)ASCIIMessageData[iIndex].MessageData).SubString(1, sizeof(ASCIIMessageData[0].MessageData));
            mASCIIText1->Text = asData;
        }
        else
            mASCIIText1->Text = "";
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::lbMesageListDrawItem(TWinControl *Control,
      int Index, TRect &Rect, TOwnerDrawState State)
{
        // ASCII 문구 선택시 문구크기 색상 다르게 적용
        int     Offset = 2;
        TCanvas *pCanvas = ((TTntListBox *)Control)->Canvas;

        pCanvas->FillRect(Rect);
        if(Index == lbMesageList->ItemIndex)
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
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::cbWaitTimeClick(TObject *Sender)
{
        WaitTime = StrToIntDef(cbWaitTime->Text, 4) * 1000;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::btnMsgEraseClick(TObject *Sender)
{
        if(tnEnv->Visible)
        {
            AnsiString asData = "";
            char Buf[3];
            char Buf2[3];
            for(int i = 0; i < 11; i++)
            {
                for(int j = 0; j < 3; j++)
                {
                    ProtocolData.ProtocolDispControl[i][j] = 1;
                    ProtocolData.ProtocolDispType[i][j]  = 1;
                    ProtocolData.ProtocolCodeKind[i][j]  = 0;
                    ProtocolData.ProtocolFontSize[i][j]  = 0;
                    asData = "효과없음";
                    ZeroMemory(&ProtocolData.ProtocolEntEffect[i][j], sizeof(ProtocolData.ProtocolEntEffect[0][0]));
                    memcpy(&ProtocolData.ProtocolEntEffect[i][j][0], asData.c_str(), asData.Length());
                    ZeroMemory(&ProtocolData.ProtocolLasDirect[i][j], sizeof(ProtocolData.ProtocolLasDirect[0][0]));
                    memcpy(&ProtocolData.ProtocolLasDirect[i][j][0], asData.c_str(), asData.Length());
                    asData = "방향 없음";
                    ZeroMemory(&ProtocolData.ProtocolEntDirect[i][j], sizeof(ProtocolData.ProtocolEntDirect[0][0]));
                    memcpy(&ProtocolData.ProtocolEntDirect[i][j][0], asData.c_str(), asData.Length());
                    ZeroMemory(&ProtocolData.ProtocolLasDirect[i][j], sizeof(ProtocolData.ProtocolLasDirect[0][0]));
                    memcpy(&ProtocolData.ProtocolLasDirect[i][j][0], asData.c_str(), asData.Length());
                    asData = "20";
                    ZeroMemory(&ProtocolData.ProtocolSpeed[i][j], sizeof(ProtocolData.ProtocolSpeed[0][0]));
                    memcpy(&ProtocolData.ProtocolSpeed[i][j][0], asData.c_str(), asData.Length());
                    asData = "2";
                    ZeroMemory(&ProtocolData.ProtocolDelay[i][j], sizeof(ProtocolData.ProtocolDelay[0][0]));
                    memcpy(&ProtocolData.ProtocolDelay[i][j][0], asData.c_str(), asData.Length());
                    ProtocolData.ProtocolStartXPos[i][j] = 0;
                    ProtocolData.ProtocolStartYPos[i][j] = 0;
                    ProtocolData.ProtocolEndXPos[i][j]   = 0;
                    ProtocolData.ProtocolEndYPos[i][j]   = 0;
                    ProtocolData.ProtocolBGImage[i][j]   = 0;
                    ProtocolData.ProtocolColorType[i][j] = 0;
                    asData = "1";
                    ZeroMemory(&ProtocolData.ProtocolColor[i][j], sizeof(ProtocolData.ProtocolColor[0][0]));
                    memcpy(&ProtocolData.ProtocolColor[i][j][0], asData.c_str(), asData.Length());
                    asData = "0";
                    ZeroMemory(&ProtocolData.ProtocolBGColor[i][j], sizeof(ProtocolData.ProtocolBGColor[0][0]));
                    memcpy(&ProtocolData.ProtocolBGColor[i][j][0], asData.c_str(), asData.Length());
                    if(i == 0)
                        asData = "RealTime Message 0-" + IntToStr(j);
                    else
                        asData = "Page Message " + IntToStr(i - 1) + "-" + IntToStr(j);
                    ZeroMemory(&ProtocolData.ProtocolData[i][j], sizeof(ProtocolData.ProtocolData[0][0]));
                    memcpy(&ProtocolData.ProtocolData[i][j][0], asData.c_str(), asData.Length());
                    AnsiString EditFileName = BasePath + IncludeTrailingPathDelimiter(FormMain->DestSysPath)
                        + "EnvData" + IntToStr(i) + IntToStr(j) + ".dat";
                    if(FileExists(EditFileName))
                        DeleteFile(EditFileName);
                }
            }
            rbTextNoRoadClick(this);
        }
        else
        {
            AnsiString ASCIIMessageFileName = BasePath + IncludeTrailingPathDelimiter(DestSysPath) + "ASCIIMessageData.dat";
            ZeroMemory(&ASCIIMessageData, sizeof(ASCIIMessageData));
            TFileStream *ASCIIMessageFile = NULL;
            AnsiString asMessage = "";
            int iDataCount = 0;
            asMessage = "![000Hello world!]";
            memcpy(&ASCIIMessageData[iDataCount++].MessageData, asMessage.c_str(), asMessage.Length());
            asMessage = "![000/C1Hello /C2World!]";
            memcpy(&ASCIIMessageData[iDataCount++].MessageData, asMessage.c_str(), asMessage.Length());
            asMessage = "![000/Y0004/E0606/S1000/C7Text 123456789 Hello World!]";
            memcpy(&ASCIIMessageData[iDataCount++].MessageData, asMessage.c_str(), asMessage.Length());
            //asMessage = "![0001234!]33";
            //memcpy(&ASCIIMessageData[iDataCount++].MessageData, asMessage.c_str(), asMessage.Length());
            asMessage = "![0032/P0000/D9901/F0003/E0101/S2002/X0000/Y0000/B000/C3/G0/T0!]";
            memcpy(&ASCIIMessageData[10].MessageData, asMessage.c_str(), asMessage.Length());
            iDataCount = 11;
            try{
                ASCIIMessageFile = new TFileStream(ASCIIMessageFileName, fmCreate | fmShareCompat);
                ASCIIMessageFile->Write(&ASCIIMessageData, sizeof(ASCIIMessageData[0]) * iDataCount);
            }catch(...){
            }
            if(ASCIIMessageFile)
            {
                delete ASCIIMessageFile;
                ASCIIMessageFile = NULL;
            }
            try{
                TTntRichEdit *tMemo;
                for(int i = 0; i < 10; i++)
                {
                    tMemo = dynamic_cast<TTntRichEdit*>(FindComponent("mASCIIText" + IntToStr(i + 1)));
                    if(tMemo)
                    {
                        AnsiString asData = AnsiString((char*)ASCIIMessageData[i].MessageData).SubString(1, sizeof(ASCIIMessageData[0].MessageData));
                        tMemo->Text = asData;
                    }
                    ASCIIMessageFileName = BasePath + IncludeTrailingPathDelimiter(FormMain->DestSysPath) + "ASCIIData" + IntToStr(i + 1) + ".dat";
                    if(FileExists(ASCIIMessageFileName))
                        DeleteFile(ASCIIMessageFileName);
                }
            }catch(...){
            }
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::eDataKeyDown(TObject *Sender, WORD &Key,
      TShiftState Shift)
{
        if(Key == VK_RETURN)
        {
            Key = 0;
            return;
        }
        if(Shift.Contains(ssCtrl))
        {
            switch (Key)
            {
                case 'V':
                {
                    bTextKeyDown = true;
                }
                break;
            }
        }
        /*TTextAttributes *SelAttributes = eData->SelAttributes;
        SelAttributes->Name = "굴림체";
        SelAttributes->Color = clWhite;
        SelAttributes->Size = 13;
        SelAttributes->Style = SelAttributes->Style << fsBold;*/
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::eDataKeyUp(TObject *Sender, WORD &Key,
      TShiftState Shift)
{
        if(bTextKeyDown)
        {
            eData->SelStart = 0;
            eData->SelLength = eData->Text.Length();
            eData->SelAttributes->Charset = eData->Font->Charset;
            eData->SelAttributes->Name = eData->Font->Name;
            eData->SelAttributes->Style = eData->Font->Style;
            eData->SelAttributes->Color = CaptionTextColor;
            eData->SelAttributes->Size = 11;
            eData->SelStart = eData->Text.Length();
            bTextKeyDown = false;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::eColorKeyDown(TObject *Sender, WORD &Key,
      TShiftState Shift)
{
        if(Key == VK_RETURN)
        {
            Key = 0;
            return;
        }
        if(Shift.Contains(ssCtrl))
        {
            switch (Key)
            {
                case 'V':
                {
                    bTextKeyDown = true;
                }
                break;
            }
        }
        /*TTextAttributes *SelAttributes = eColor->SelAttributes;
        SelAttributes->Name = "굴림체";
        SelAttributes->Color = clWhite;
        SelAttributes->Size = 13;
        SelAttributes->Style = SelAttributes->Style << fsBold;*/
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::eBGColorKeyUp(TObject *Sender, WORD &Key,
      TShiftState Shift)
{
        if(bTextKeyDown)
        {
            eBGColor->SelStart = 0;
            eBGColor->SelLength = eBGColor->Text.Length();
            eBGColor->SelAttributes->Charset = eBGColor->Font->Charset;
            eBGColor->SelAttributes->Name = eBGColor->Font->Name;
            eBGColor->SelAttributes->Style = eBGColor->Font->Style;
            eBGColor->SelAttributes->Color = CaptionTextColor;
            eBGColor->SelAttributes->Size = 11;
            eBGColor->SelStart = eBGColor->Text.Length();
            bTextKeyDown = false;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::eBGColorKeyDown(TObject *Sender, WORD &Key,
      TShiftState Shift)
{
        if(Key == VK_RETURN)
        {
            Key = 0;
            return;
        }
        if(Shift.Contains(ssCtrl))
        {
            switch (Key)
            {
                case 'V':
                {
                    bTextKeyDown = true;
                }
                break;
            }
        }
        /*TTextAttributes *SelAttributes = eBGColor->SelAttributes;
        SelAttributes->Name = "굴림체";
        SelAttributes->Color = clWhite;
        SelAttributes->Size = 13;
        SelAttributes->Style = SelAttributes->Style << fsBold;*/
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::eColorKeyUp(TObject *Sender, WORD &Key,
      TShiftState Shift)
{
        if(bTextKeyDown)
        {
            eColor->SelStart = 0;
            eColor->SelLength = eColor->Text.Length();
            eColor->SelAttributes->Charset = eColor->Font->Charset;
            eColor->SelAttributes->Name = eColor->Font->Name;      
            eColor->SelAttributes->Style = eColor->Font->Style;
            eColor->SelAttributes->Color = CaptionTextColor;
            eColor->SelAttributes->Size = 11;
            eColor->SelStart = eColor->Text.Length();
            bTextKeyDown = false;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::SpeedButtonPreviewClick(TObject *Sender)
{
        //미리보기 버튼
        AnsiString destFolder = BasePath;
        AnsiString destFile = destFolder + "DABITPreview.exe";
        if(SpeedButtonPreview->Down)
        {
            AnsiString tempData = "";
            try{
                ProtocolTabKind = 0;
                int iCurBlockNo = 0;
                int iCurPageNo = 0;
                if(rbNo1->Checked)
                    ProtocolBlockNo = 0;
                else if(rbNo2->Checked)
                    ProtocolBlockNo = 1;
                else if(rbNo3->Checked)
                    ProtocolBlockNo = 2;
                iCurBlockNo = ProtocolBlockNo;
                iCurPageNo = 0;
                MessageKind = 0;
                if(rbPageNo2->Checked)
                {
                    ProtocolBlockNo2 = ProtocolBlockNo;
                    iCurPageNo = cbPageNo->ItemIndex + 1;
                    ProtocolPageNo2 = iCurPageNo - 1;
                    MessageKind = 1;
                    ProtocolPageNo = cbPageNo->ItemIndex + 1;
                }
                WideString wtempData;
                AnsiString tempData;
                AnsiString EditFileName;
                // 선택한 긴급문구 구조체에 저장
                if(rbPageNo2->Checked)
                    iCurPageNo = cbPageNo->ItemIndex + 1;
                //if(cbDispControl->ItemIndex == 0 || cbDispControl->ItemIndex == 1)
                    ProtocolData.ProtocolDispControl[iCurPageNo][iCurBlockNo] = cbDispControl->ItemIndex;
                //else
                //    ProtocolData.ProtocolDispControl[iCurPageNo][iCurBlockNo] = StrToIntDef(cbDispControl->Text, 1);
                ProtocolData.ProtocolDispType[iCurPageNo][iCurBlockNo] = cbDispType->ItemIndex;
                ProtocolData.ProtocolCodeKind[iCurPageNo][iCurBlockNo] = cbCodeKind->ItemIndex;
                ProtocolData.ProtocolFontSize[iCurPageNo][iCurBlockNo] = cbFontSize->ItemIndex;
                ProtocolData.ProtocolFontKind[iCurPageNo][iCurBlockNo] = cbFontKind->ItemIndex;
                tempData = cbEntEffect->Text;
                ZeroMemory(&ProtocolData.ProtocolEntEffect[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolEntEffect[0][0]));
                memcpy(&ProtocolData.ProtocolEntEffect[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                tempData = cbEntDirect->Text;
                ZeroMemory(&ProtocolData.ProtocolEntDirect[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolEntDirect[0][0]));
                memcpy(&ProtocolData.ProtocolEntDirect[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                tempData = cbLasEffect->Text;
                ZeroMemory(&ProtocolData.ProtocolLasEffect[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolLasEffect[0][0]));
                memcpy(&ProtocolData.ProtocolLasEffect[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                tempData = cbLasDirect->Text;
                ZeroMemory(&ProtocolData.ProtocolLasDirect[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolLasDirect[0][0]));
                memcpy(&ProtocolData.ProtocolLasDirect[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                ProtocolData.ProtocolColorType[iCurPageNo][iCurBlockNo] = cbAssistEffect->ItemIndex;
                tempData = cbSpeed->Text;
                ZeroMemory(&ProtocolData.ProtocolSpeed[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolSpeed[0][0]));
                memcpy(&ProtocolData.ProtocolSpeed[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                tempData = cbDelay->Text;
                ZeroMemory(&ProtocolData.ProtocolDelay[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolDelay[0][0]));
                memcpy(&ProtocolData.ProtocolDelay[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                ProtocolData.ProtocolStartXPos[iCurPageNo][iCurBlockNo] = cbStartXPos->ItemIndex;
                ProtocolData.ProtocolStartYPos[iCurPageNo][iCurBlockNo] = cbStartYPos->ItemIndex;
                ProtocolData.ProtocolEndXPos[iCurPageNo][iCurBlockNo] = cbEndXPos->ItemIndex;
                ProtocolData.ProtocolEndYPos[iCurPageNo][iCurBlockNo] = cbEndYPos->ItemIndex;
                ProtocolData.ProtocolBGImage[iCurPageNo][iCurBlockNo] = cbBGImage->ItemIndex;
                tempData = eColor->Text;
                ZeroMemory(&ProtocolData.ProtocolColor[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolColor[0][0]));
                memcpy(&ProtocolData.ProtocolColor[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                tempData = eBGColor->Text;
                ZeroMemory(&ProtocolData.ProtocolBGColor[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolBGColor[0][0]));
                memcpy(&ProtocolData.ProtocolBGColor[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                tempData = eData->Text;
                wtempData = eData->Text;
                ZeroMemory(&ProtocolData.ProtocolData[iCurPageNo][iCurBlockNo], sizeof(ProtocolData.ProtocolData[0][0]));
                memcpy(&ProtocolData.ProtocolData[iCurPageNo][iCurBlockNo][0], tempData.c_str(), tempData.Length());
                EditFileName = BasePath + IncludeTrailingPathDelimiter(DestSysPath)
                    + "EnvData" + IntToStr(iCurPageNo) + IntToStr(iCurBlockNo) + ".dat";
                eData->Lines->SaveToFile(EditFileName);
                SaveIniFile();
                PreviewMode = 0;
                PreviewSaveIniFile();
                //미리보기 창 실행시켜 줌
                HINSTANCE Ret = ShellExecute(NULL, "open", destFile.c_str(), destFolder.c_str(), destFolder.c_str(), SW_SHOWNORMAL);
                if((int)Ret <= 32)
                {
                    AnsiString asMessage = "DABITPreview.exe File Not Open.";
                    Message("\r\n" + asMessage + "\r\n", LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                }
            }catch(...){
            }
        }
        else
        {
            //미리보기 창이 켜 있을때 종료시켜 줌
            AnsiString WinName = "";
            HWND hWnd = NULL;
            WinName = ExtractFileName(destFile.SubString(1, destFile.Length() - 4));
            try{
                hWnd = FindWindow(NULL, WinName.c_str());
                if(hWnd)
                {
                    SendMessage(hWnd, WM_CLOSE, 0, 0);
                    Sleep(500);
                }
            }catch(...){
            }
        }
        PreviewButtonTimer->Enabled = SpeedButtonPreview->Down;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::SBASCIIPreviewClick(TObject *Sender)
{
        //미리보기 버튼
        AnsiString destFolder = BasePath;
        AnsiString destFile = destFolder + "DABITPreview.exe";
        if(SBASCIIPreview->Down)
        {
            AnsiString tempData = "";
            try{
                if(PreviewMode <= 0)
                    PreviewMode = 1;
                SaveIniFile();
                PreviewSaveIniFile();
                //미리보기 창 실행시켜 줌
                HINSTANCE Ret = ShellExecute(NULL, "open", destFile.c_str(), destFolder.c_str(), destFolder.c_str(), SW_SHOWNORMAL);
                if((int)Ret <= 32)
                {
                    AnsiString asMessage = "DABITPreview.exe File Not Open.";
                    Message("\r\n" + asMessage + "\r\n", LangFile.MessageButton04, "", "", asMessage.Length() - 2, 3, 1, clRed);
                }
            }catch(...){
            }
        }
        else
        {
            //미리보기 창이 켜 있을때 종료시켜 줌
            AnsiString WinName = "";
            HWND hWnd = NULL;
            WinName = ExtractFileName(destFile.SubString(1, destFile.Length() - 4));
            try{
                hWnd = FindWindow(NULL, WinName.c_str());
                if(hWnd)
                {
                    SendMessage(hWnd, WM_CLOSE, 0, 0);
                    Sleep(500);
                }
            }catch(...){
            }
        }
        PreviewButtonTimer->Enabled = SBASCIIPreview->Down;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::PreviewButtonTimerTimer(TObject *Sender)
{
        //미리보기 버튼이 다운 됬을때 업시켜주는 타이머
        if(SpeedButtonPreview->Down || SBASCIIPreview->Down)
        {
            AnsiString destFolder = BasePath;
            AnsiString destFile = destFolder + "DABITPreview.exe";
            AnsiString WinName = "";
            HWND hWnd = NULL;
            WinName = ExtractFileName(destFile.SubString(1, destFile.Length() - 4));
            try{
                hWnd = FindWindow(NULL, WinName.c_str());
                if(!hWnd)
                {
                    SpeedButtonPreview->Down = false;
                    SBASCIIPreview->Down = false;
                    PreviewButtonTimer->Enabled = false;
                }
            }catch(...){
            }
        }
        else
            PreviewButtonTimer->Enabled = false;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::mASCIITextClick(TObject *Sender)
{
        int iIndex = 0;
        TTntMemo *tMemo;
        try{
            for(int i = 1; i <= 9; i++)
            {
                tMemo = dynamic_cast<TTntMemo*>(FindComponent("mASCIIText" + IntToStr(i)));
                if(tMemo == Sender)
                {
                    PreviewMode = i;
                    break;
                }
            }
        }catch(...){
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::mASCIITextMouseDown(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
        int iIndex = 0;
        TTntRichEdit *tMemo;
        try{
            for(int i = 1; i <= 10; i++)
            {
                tMemo = dynamic_cast<TTntRichEdit*>(FindComponent("mASCIIText" + IntToStr(i)));
                if(tMemo == Sender)
                {
                    PreviewMode = i;
                    break;
                }
            }
        }catch(...){
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::mASCIITextKeyDown(TObject *Sender, WORD &Key,
      TShiftState Shift)
{
        if(Key == VK_RETURN)
        {
            Key = 0;
            return;
        }
        if(Shift.Contains(ssCtrl))
        {
            switch (Key)
            {
                case 'V':
                {
                    bTextKeyDown = true;
                }
                break;
            }
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::mASCIITextKeyUp(TObject *Sender, WORD &Key,
      TShiftState Shift)
{
        if(bTextKeyDown)
        {
            TTntRichEdit *tMemo;
            try{
                for(int i = 0; i < 10; i++)
                {
                    tMemo = dynamic_cast<TTntRichEdit*>(FindComponent("mASCIIText" + IntToStr(i + 1)));
                    if(tMemo)
                    {
                        if(Sender == tMemo)
                            break;
                    }
                }
            }catch(...){
            }
            if(tMemo)
            {
                tMemo->SelStart = 0;
                tMemo->SelLength = tMemo->Text.Length();
                tMemo->SelAttributes->Charset = tMemo->Font->Charset;
                tMemo->SelAttributes->Name = tMemo->Font->Name;
                tMemo->SelAttributes->Style = tMemo->Font->Style;
                tMemo->SelAttributes->Color = CaptionTextColor;
                tMemo->SelAttributes->Size = 11;
                tMemo->SelStart = tMemo->Text.Length();
            }
            bTextKeyDown = false;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnFactoryResetClick(TObject *Sender)
{
        // ASCII Message 설정 셋팅 창 열기 버튼
        FASCIISet = new TFormASCIISet(this);
        FASCIISet->ShowModal();
        SaveIniFile();
        if(FASCIISet)
        {
            delete FASCIISet;
            FASCIISet = NULL;
        }
}
//---------------------------------------------------------------------------
                                            
void __fastcall TFormMain::tsASCIIMessageClick(TObject *Sender)
{
        if(Sender == tsASCIIMessage)
        {
            ProtocolTabKind = 1;
            tnASCIIMessage->Visible = true;
            tsASCIIMessage->Flat = true;
            tnEnv->Visible = false;
            tsEnv->Flat = false;
            tnSpecial->Visible = false;
            tsSpecial->Flat = false;
            tnInfor->Visible = false;
            tsInfor->Flat = false;
            pnASCII->Color = CaptionPanelColor;
            pnSpecial->Color = MainColor;
            pnTapInfor->Color = MainColor;
            tsASCIIMessage->Font->Color = TextBGColor;
            tsSpecial->Font->Color = TextColor;
            tsInfor->Font->Color = TextColor;
            pnDataSend->Visible = false;
            pnBtnMsgErase->Visible = true;
        }
        else if(Sender == tsSpecial)
        {
            ProtocolTabKind = 2;
            tnSpecial->Visible = true;
            tsSpecial->Flat = true;
            tnASCIIMessage->Visible = false;
            tsASCIIMessage->Flat = false;
            tnEnv->Visible = false;
            tsEnv->Flat = false;
            tnInfor->Visible = false;
            tsInfor->Flat = false;
            pnASCII->Color = MainColor;
            pnSpecial->Color = CaptionPanelColor;
            pnTapInfor->Color = MainColor;
            tsASCIIMessage->Font->Color = TextColor;
            tsEnv->Font->Color = TextColor;
            tsSpecial->Font->Color = TextBGColor;
            tsInfor->Font->Color = TextColor;
            pnDataSend->Visible = false;
            pnBtnMsgErase->Visible = false;
        }
        else if(Sender == tsInfor)
        {
            ProtocolTabKind = 3;
            tnInfor->Visible = true;
            tsInfor->Flat = true;
            tnASCIIMessage->Visible = false;
            tsASCIIMessage->Flat = false;
            tnEnv->Visible = false;
            tsEnv->Flat = false;
            tnSpecial->Visible = false;
            tsSpecial->Flat = false;
            pnASCII->Color = MainColor;
            pnSpecial->Color = MainColor;
            tsSpecial->Flat = false;
            pnTapInfor->Color = CaptionPanelColor;
            tsASCIIMessage->Font->Color = TextColor;
            tsEnv->Font->Color = TextColor;
            tsSpecial->Font->Color = TextColor;
            tsInfor->Font->Color = TextBGColor;
            pnDataSend->Visible = false;
            pnBtnMsgErase->Visible = false;
        }
        else
        {
            ProtocolTabKind = 0;
            tnEnv->Visible = true;
            tsEnv->Flat = true;
            tnASCIIMessage->Visible = false;
            tsASCIIMessage->Flat = false;
            tnSpecial->Visible = false;
            tsSpecial->Flat = false;
            tnInfor->Visible = false;
            tsInfor->Flat = false;
            pnASCII->Color = CaptionPanelColor;
            pnSpecial->Color = MainColor;
            pnTapInfor->Color = MainColor;
            tsEnv->Font->Color = TextBGColor;
            tsSpecial->Font->Color = TextColor;
            tsInfor->Font->Color = TextColor;
            pnDataSend->Visible = true;
            pnBtnMsgErase->Visible = true;
        }
        //if(tnEnv->Visible)
        //    laAddress->Left = pnBtnMsgErase->Left + pnBtnMsgErase->Width + 8;
        //else
            laAddress->Left = 16;
        cbDIBDAddress->Left = laAddress->Left + laAddress->Width + 4;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::btnSetupClick(TObject *Sender)
{
        FSetting = new TFormSetting(this);
        FSetting->ShowModal();
        if(FSetting->Tag == 1)
        {
            ModuleHeight = FSetting->UpDownHeight->Position;
            ModuleWidth = FSetting->UpDownWidth->Position;
            Direct = FSetting->cbDirection->ItemIndex;
            DirectCheck = FSetting->cbDirectCheck->Checked;
            if(FSetting->rgASCII->Checked)
            {
                PorotocolType = 1;
                ProtocolTabKind = 1;

            }
            else
            {
                PorotocolType = 0;
                ProtocolTabKind = 0;
            }
            ShowSetting();
        }
        if(FSetting)
        {
            delete FSetting;
            FSetting = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::ShowSetting()
{
        try{
            if(ComKind == 0)
            {
                laAddress->Visible = bRS485;
                //laAddress2->Visible = laAddress->Visible;
                cbDIBDAddress->Visible = laAddress->Visible;
                tnSignAddr->Visible = laAddress->Visible;
            }
            else
                tnSignAddr->Visible = false;
            //tnSignAddr->Visible = tnSignAddr->Visible;
            if(PorotocolType == 1)
            {
                tsASCIIMessage->Visible = true;
                tsEnv->Visible = false;
                tnEnv->Visible = false;
                if(ProtocolTabKind == 2)
                {
                    tsSpecial->Click();
                    /*tnSettings->Visible = true;
                    tnASCIIMessage->Visible = false;
                    tnSpecial->Visible = false;
                    pnBtnMsgErase->Visible = false;
                    pnASCII->Color = MainColor;
                    pnSpecial->Color = MainColor;
                    pnTapSettings->Color = TextBGColor;*/
                }
                else if(ProtocolTabKind == 3)
                {
                    tsInfor->Click();
                    /*tnSpecial->Visible = true;
                    tnASCIIMessage->Visible = false;
                    tnSettings->Visible = false;
                    pnBtnMsgErase->Visible = false;
                    pnASCII->Color = MainColor;
                    pnSpecial->Color = TextBGColor;
                    pnTapSettings->Color = MainColor;*/
                }
                else
                {
                    tsASCIIMessage->Click();
                    /*tnSettings->Visible = false;
                    tnASCIIMessage->Visible = true;
                    tnSpecial->Visible = false;
                    pnBtnMsgErase->Visible = true;
                    pnASCII->Color = TextBGColor;
                    pnSpecial->Color = MainColor;
                    pnTapSettings->Color = MainColor;*/
                }
                //pcMessage->ActivePage = tsASCIIMessage;
                tnSignAddr->Top = tnASCIIMessage->Top + tnASCIIMessage->Height;
                tnSpecial->Height = tnASCIIMessage->Height;
                tnInfor->Height = tnASCIIMessage->Height;
                if(tnSignAddr->Visible)//572
                    this->Height = 620 + tnSignAddr->Height + pnLog->Height;
                else
                    this->Height = 620 + pnLog->Height;
                pnDataSend->Visible = false;
                pnBtnMsgErase->Top = tnASCIIMessage->Top + pnBtnFactoryReset->Top;
            }
            else
            {
                tsASCIIMessage->Visible = false;
                tnASCIIMessage->Visible = false;
                tsEnv->Visible = true;
                if(ProtocolTabKind == 2)
                {
                    tsSpecial->Click();
                    /*tnSettings->Visible = true;
                    tnEnv->Visible = false;
                    tnSpecial->Visible = false;
                    pnASCII->Color = MainColor;
                    pnSpecial->Color = MainColor;
                    pnTapSettings->Color = TextBGColor;
                    pnDataSend->Visible = false;
                    pnBtnMsgErase->Visible = false;*/
                }
                else if(ProtocolTabKind == 3)
                {
                    tsInfor->Click();
                    /*tnSpecial->Visible = true;
                    tnEnv->Visible = false;
                    tnSettings->Visible = false;
                    pnBtnMsgErase->Visible = false;
                    pnASCII->Color = MainColor;
                    pnSpecial->Color = TextBGColor;
                    pnTapSettings->Color = MainColor;*/
                }
                else
                {
                    tsEnv->Click();
                    /*tnEnv->Visible = true;
                    tnSpecial->Visible = false;
                    tnSettings->Visible = false;
                    pnASCII->Color = TextBGColor;
                    pnSpecial->Color = MainColor;
                    pnTapSettings->Color = MainColor;
                    pnDataSend->Visible = true;
                    pnBtnMsgErase->Visible = true;*/
                }
                //this->Height = 652 + pnLog->Height;
                //pcMessage->ActivePage = tsEnv;
                //tnEnv->Visible = true;
                tnSignAddr->Top = tnEnv->Top + tnEnv->Height;
                tnSpecial->Height = tnEnv->Height;
                tnInfor->Height = tnEnv->Height;
                pnSpeedButtonPreview->Top = eData->Top + eData->Height + 8;
                if(tnSignAddr->Visible)
                    this->Height = 620 + tnSignAddr->Height + pnLog->Height;
                else
                    this->Height = 620 + pnLog->Height;
                //pnDataSend->Top = tnSignAddr->Top + 4;
                pnBtnMsgErase->Top = pnDataSend->Top;
            }
            //if(tnEnv->Visible)
            //    laAddress->Left = pnBtnMsgErase->Left + pnBtnMsgErase->Width + 8;
            //else
                laAddress->Left = 16;
            cbDIBDAddress->Left = laAddress->Left + laAddress->Width + 4;
            //pnLog->Top = tnSignAddr->Top + tnSignAddr->Height;
            pnBtnPacketRead->Visible = bPacketOpen;
            pnMain->Height = this->Height - 7;
        }catch(...){
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::btnHelpClick(TObject *Sender)
{
        AnsiString strUrl = "http://dabitsol.com/?page_id=82";
        ShellExecute(NULL, "open", "iexplore.exe", strUrl.c_str(), NULL, SW_SHOWNORMAL);
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::btnHideClick(TObject *Sender)
{
        this->WindowState = wsMinimized;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::btnMaxClick(TObject *Sender)
{
        if(this->WindowState == wsMaximized)
            this->WindowState = wsNormal;
        else
            this->WindowState = wsMaximized;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::btnExitClick(TObject *Sender)
{
        Close();
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::ApplicationEvents1ShortCut(TWMKey &Msg,
      bool &Handled)
{
        switch(Msg.CharCode)
        {
            case VK_F11 :
            {
                pnBtnPacketRead->Visible = !pnBtnPacketRead->Visible;
            }
            break;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::pnCaptionMouseDown(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
        if(Button == mbLeft)
        {
            OldXPos = X;
            OldYPos = Y;
            NewXPos = X - OldXPos;
            NewYPos = Y - OldYPos;
            bCaptionMove = true;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::pnCaptionMouseMove(TObject *Sender,
      TShiftState Shift, int X, int Y)
{
        if(bCaptionMove)
        {
            NewXPos = X - OldXPos;
            NewYPos = Y - OldYPos;
            this->Left = this->Left + NewXPos;
            this->Top = this->Top + NewYPos;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::pnCaptionMouseUp(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
        if(Button == mbLeft)
        {
            bCaptionMove = false;
            NewXPos = X - OldXPos;
            NewYPos = Y - OldYPos;
            this->Left = this->Left + NewXPos;
            this->Top = this->Top + NewYPos;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnComPortClick(TObject *Sender)
{
        // 통신 설정창 열기 버튼
        FComPort = new TFormComPort(this);
        FComPort->Top = this->Top;
        FComPort->Left = this->Left + (this->Width - FComPort->Width) / 2;
        FComPort->ShowModal();
        ShowSetting();
        if(FComPort)
        {
            delete FComPort;
            FComPort = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnETCClick(TObject *Sender)
{
        FETC = new TFormETC(this);
        FETC->Top = this->Top;
        FETC->Left = this->Left + (this->Width - FETC->Width) / 2;
        FETC->ShowModal();
        if(FETC)
        {
            delete FETC;
            FETC = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnFontSendClick(TObject *Sender)
{
        // 폰트 전송 창 열기 버튼
        FFont = new TFormFont(this);
        FFont->Top = this->Top;
        FFont->Left = this->Left + (this->Width - FFont->Width) / 2;
        FFont->ShowModal();
        SaveIniFile();
        if(FFont)
        {
            delete FFont;
            FFont = NULL;
        }  
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnLEDSettingClick(TObject *Sender)
{
        // LED 모듈 설정 셋팅 창 열기 버튼
        FLEDSet = new TFormLEDSet(this);
        FLEDSet->Top = this->Top;
        FLEDSet->Left = this->Left + (this->Width - FLEDSet->Width) / 2;
        FLEDSet->ShowModal();
        SaveIniFile();
        if(FLEDSet)
        {
            delete FLEDSet;
            FLEDSet = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnDIBDDownloadClick(TObject *Sender)
{
        // 펌웨어 전송창 열기 버튼
        FFirmware = new TFormFirmware(this);
        FFirmware->Top = this->Top;
        FFirmware->Left = this->Left + (this->Width - FFirmware->Width) / 2;
        FFirmware->ShowModal();
        SaveIniFile();
        if(FFirmware)
        {
            delete FFirmware;
            FFirmware = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnLogClick(TObject *Sender)
{
        // 고급 설정창 열기 버튼
        //FormLog = new TFormLog(this);
        FormLog->Show();
        //if(FLog)
        //{
        //    delete FLog;
        //    FLog = NULL;
        //}
}
//---------------------------------------------------------------------------
                                                        
void __fastcall TFormMain::BtnDABITOptionClick(TObject *Sender)
{
        // DABIT Option 설정창 열기 버튼
        FDABITOption = new TFormDABITOption(this);
        FDABITOption->Top = this->Top;
        FDABITOption->Left = this->Left + (this->Width - FDABITOption->Width) / 2;
        FDABITOption->ShowModal();
        SaveIniFile();
        if(FormMain->bOptionJ4 == 7)
        {
            cbSoundSec3->Enabled = true;
            SLabel3->Enabled = true;
            cbSoundSec4->Enabled = true;
            SLabel4->Enabled = true;
        }
        else
        {
            cbSoundSec3->Enabled = false;
            SLabel3->Enabled = false;
            cbSoundSec4->Enabled = false;
            SLabel4->Enabled = false;
        }
        if(FDABITOption)
        {
            delete FDABITOption;
            FDABITOption = NULL;
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnApplyClick(TObject *Sender)
{
        ModuleHeight = UpDownHeight->Position;
        ModuleWidth = UpDownWidth->Position;
        Direct = cbDirection->ItemIndex;
        DirectCheck = cbDirectCheck->Checked;
        //if(rgASCII->Checked)
        if(cbProtocol->ItemIndex == 1)
        {
            PorotocolType = 0;
            //ProtocolTabKind = 0;

        }
        else
        {
            PorotocolType = 1;
            //ProtocolTabKind = 1;
        }
        ShowSetting();
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::cbDirectCheckClick(TObject *Sender)
{
        //TntLabel10->Enabled = cbDirectCheck->Checked;
        cbDirection->Enabled = cbDirectCheck->Checked;        
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::TntLabel10Click(TObject *Sender)
{
        cbDirectCheck->Checked = !cbDirectCheck->Checked;
        //TntLabel10->Enabled = cbDirectCheck->Checked;
        cbDirection->Enabled = cbDirectCheck->Checked;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnLogClearClick(TObject *Sender)
{
        RichEditLog->Clear();        
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::rgASCIIClick(TObject *Sender)
{
        BtnApplyClick(Sender);
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnMemInputClick(TObject *Sender)
{
        bStopSend = false;
        int Addr = 0;
        AnsiString Data = "";
        if(bRS485)
            Addr = Controler1;
        BYTE SendData[10];
        SendData[0] = 0x00;
        BYTE tempOpCode = 0;
        int iSendSize = 1;
        AnsiString asMessage = "";
        // 일반문구 갯수 등록
        SendData[0] = cbMsgInput->ItemIndex + 1;
        MessageCount = SendData[0];
        tempOpCode = FMI;
        asMessage = LangFile.Message09;
        bool Connect = false;
        ButtonEnabled(false);
        if(ComPortSet(ComKind, PortUse1, BaudRateUse1, Disable, Disable, IPAddress, IPPort, false))
        {
            if(PorotocolType == 1)
            {
                AnsiString asSendData = "![" + IntTo485Address(Addr) + "060";
                char Buf[3];
                ZeroMemory(Buf, sizeof(Buf));
                wsprintf(Buf, "%02d", SendData[0]);
                asSendData = asSendData + AnsiString(Buf) + "!]";
                iSendSize = asSendData.Length();
                memcpy(SendData, asSendData.c_str(), iSendSize);
                if(ModuleASCIIData(ComKind, Addr, SendData, iSendSize, bMsg))
                {
                    if(bDataFail)
                        AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
                    else
                        AddLog(asMessage + " [OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
            }
            else
            {
                Connect = ModuleData(ComKind, Addr, SendData, iSendSize, 0, (OPCode)tempOpCode, bMsg);
                if(Connect)
                {
                    if(bDataFail)
                        AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
                    else
                        AddLog(asMessage + " [OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
            }
            ComClose(ComKind);
        }
        if(MessageCount != cbMsgInput->ItemIndex + 1) // 일반문구 갯수 이외의 구조체 초기화
        {
            char Buf[3];
            char Buf2[3];
            AnsiString asFileName = "";
            AnsiString asData = "";
            for(int i = 1; i <= MessageCount; i++)
            {
                for(int j = 0; j < 3; j++)
                {
                    if(j == 0)
                        ProtocolData.ProtocolDispControl[i][j] = 1;
                    else
                        ProtocolData.ProtocolDispControl[i][j] = 0;
                    ProtocolData.ProtocolDispType[i][j]  = 0;
                    ProtocolData.ProtocolCodeKind[i][j]  = 0;
                    ProtocolData.ProtocolFontSize[i][j]  = 0;
                    asData = "???????";
                    memcpy(&ProtocolData.ProtocolEntEffect[i][j][0], asData.c_str(), asData.Length());
                    memcpy(&ProtocolData.ProtocolLasDirect[i][j][0], asData.c_str(), asData.Length());
                    asData = "???? ????";
                    memcpy(&ProtocolData.ProtocolEntDirect[i][j][0], asData.c_str(), asData.Length());
                    memcpy(&ProtocolData.ProtocolLasDirect[i][j][0], asData.c_str(), asData.Length());
                    ProtocolData.ProtocolStartXPos[i][j] = 0;
                    ProtocolData.ProtocolStartYPos[i][j] = 0;
                    ProtocolData.ProtocolEndXPos[i][j] = 0;
                    ProtocolData.ProtocolEndYPos[i][j] = 0;
                    ProtocolData.ProtocolBGImage[i][j] = 0;
                    ProtocolData.ProtocolColorType[i][j] = 0;
                    asData = "20";
                    memcpy(&ProtocolData.ProtocolSpeed[i][j][0], asData.c_str(), asData.Length());
                    asData = "2";
                    memcpy(&ProtocolData.ProtocolDelay[i][j][0], asData.c_str(), asData.Length());
                    asData = "1";
                    memcpy(&ProtocolData.ProtocolColor[i][j][0], asData.c_str(), asData.Length());
                    asData = "0";
                    memcpy(&ProtocolData.ProtocolBGColor[i][j][0], asData.c_str(), asData.Length());
                    ZeroMemory(Buf, 3);
                    wsprintf(Buf, "%02d", i + 1);
                    ZeroMemory(Buf2, 3);
                    wsprintf(Buf2, "%02d", j + 1);
                    asData = LangFile.FormProtocolTab2 + AnsiString(Buf).TrimRight() + "-" + AnsiString(Buf2).TrimRight();
                    memcpy(&ProtocolData.ProtocolData[i][j][0], asData.c_str(), asData.Length());
                    asFileName = BasePath + IncludeTrailingPathDelimiter(DestSysPath) + "EnvData" + IntToStr(i) + IntToStr(j) + ".dat";
                    if(FileExists(asFileName))
                        DeleteFile(asFileName);
                }
            }
        }
        MessageCount = cbMsgInput->ItemIndex + 1;
        int PageIndex = ProtocolPageNo;
        int MsgMemClearIndex = cbMsgMemClear->ItemIndex;
        cbPageNo->Clear();
        cbMsgMemClear->Clear();
        cbMsgMemClear->Items->Add(LangFile.FormProtocolEffect9);
        for(int i = 0; i < MessageCount; i++)
        {
            cbPageNo->Items->Add(IntToStr(i + 1));
            cbMsgMemClear->Items->Add("Page " + IntToStr(i + 1));
        }
        if(MessageCount - 1 < PageIndex)
            cbPageNo->ItemIndex = MessageCount - 1;
        else
            cbPageNo->ItemIndex = PageIndex;
        if(MessageCount < MsgMemClearIndex)
            cbMsgMemClear->ItemIndex = MessageCount;
        else
            cbMsgMemClear->ItemIndex = MsgMemClearIndex;
        ProtocolPageNo = cbPageNo->ItemIndex;
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnMsgMemClearClick(TObject *Sender)
{
        bStopSend = false;
        int Addr = 0;
        AnsiString Data = "";
        if(bRS485)
            Addr = Controler1;
        BYTE SendData[11];
        SendData[0] = 0x00;
        BYTE tempOpCode = 0;
        int iSendSize = 1;
        AnsiString asMessage = "";
        // 일반문구 전체 삭제
        if(Sender == BtnBGImage)
        {
            SendData[0] = cbBGISelect->ItemIndex;
            tempOpCode = EDI;
            asMessage = FormMain->LangFile.Message10;
        }
        else
        {
            if(cbMsgMemClear->ItemIndex == 0)
                SendData[0] = 0x80;
            else
                SendData[0] = cbMsgMemClear->ItemIndex - 1;
            tempOpCode = FMMC;
            asMessage = LangFile.Message17;
        }
        bool Connect = false;
        ButtonEnabled(false);
        if(ComPortSet(ComKind, PortUse1, BaudRateUse1, Disable, Disable, IPAddress, IPPort, false))
        {
            if(PorotocolType == 1)
            {
                char Buf[4];
                ZeroMemory(Buf, sizeof(Buf));
                AnsiString asSendData = "";
                if(Sender == BtnBGImage)
                {
                    asSendData = "![" + IntTo485Address(Addr) + "020";
                    wsprintf(Buf, "%03d", SendData[0]);
                }
                else
                {
                    if(cbMsgMemClear->ItemIndex == 0)
                        SendData[0] = 99;
                    else
                        SendData[0] = cbMsgMemClear->ItemIndex - 1;
                    asSendData = "![" + IntTo485Address(Addr) + "061";
                    wsprintf(Buf, "%02d", SendData[0]);
                }
                asSendData = asSendData + AnsiString(Buf) + "!]";
                iSendSize = asSendData.Length();
                memcpy(SendData, asSendData.c_str(), iSendSize);
                if(ModuleASCIIData(ComKind, Addr, SendData, iSendSize, bMsg))
                {
                    if(bDataFail)
                        AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
                    else
                        AddLog(asMessage + " [OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
            }
            else
            {
                Connect = ModuleData(ComKind, Addr, SendData, iSendSize, 0, (OPCode)tempOpCode, bMsg);
                if(Connect)
                {
                    if(bDataFail)
                        AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
                    else
                        AddLog(asMessage + " [OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
            }
            ComClose(ComKind);
        }
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnScreenClick(TObject *Sender)
{
        bStopSend = false;
        int Addr = 0;
        AnsiString Data = "";
        if(bRS485)
            Addr = Controler1;
        BYTE SendData[11];
        BYTE ColorData[5];
        ZeroMemory(ColorData, sizeof(ColorData));
        ZeroMemory(SendData, sizeof(SendData));
        union{
            char cbSize[2];
            struct{
                unsigned FRed : 5;
                unsigned FGreen : 6;
                unsigned FBlue : 5;
            }Bit;
        }uSize;
        union{
            char cbSize;
            struct{
                unsigned FRed : 3;
                unsigned FGreen : 3;
                unsigned FBlue : 2;
            }Bit;
        }uSize2;
        COLORREF NewColor = GetColor(cbScreen->ItemIndex);
        int R = 0;
        int G = 0;
        int B = 0;
        //int RS = 0;
        R = NewColor & 0xFF;
        G = (NewColor & 0xFF00) >> 8;
        B = (NewColor & 0xFF0000) >> 16;
        //int a = int(idx/(double)idy + 0.5);반올림
        if(ProgramType == e2BPP)
        {
            ColorData[0] = 2;
            if(R > 0 && (G > 0 || B > 0))
                ColorData[1] = 0xff;
            else if(R > 0)
                ColorData[1] = 0x55;
            else if(G > 0 || B > 0)
                ColorData[1] = 0xaa;
        }
        else if(ProgramType == e3BPP || ProgramType == e8BPP)
        {
            if(ProgramType == e3BPP)
                ColorData[0] = 3;
            else
                ColorData[0] = 8;
            uSize2.Bit.FRed = R / 32;
            uSize2.Bit.FGreen = G / 32;
            uSize2.Bit.FBlue = B / 64;
            ColorData[1] = uSize2.cbSize;
        }
        /*else if(ProgramType == e16BPP)
        {
            ColorData[0] = 16;
            //RS = int(R/(double)8 + 0.5);반올림
            uSize.Bit.FRed = R / 8;
            uSize.Bit.FGreen = G / 4;
            uSize.Bit.FBlue = B / 8;
            ColorData[1] = uSize.cbSize[0];
            ColorData[2] = uSize.cbSize[1];
        }*/
        else if(ProgramType == e24BPP)
        {
            ColorData[0] = 24;
            ColorData[1] = R;
            ColorData[2] = G;
            ColorData[3] = B;
        }
        BYTE tempOpCode = SC;
        int iSendSize = 1;
        AnsiString asMessage = "";
        asMessage = LangFile.BtnScreen;
        bool Connect = false;
        ButtonEnabled(false);
        if(ComPortSet(ComKind, PortUse1, BaudRateUse1, Disable, Disable, IPAddress, IPPort, false))
        {
            if(PorotocolType == 1)
            {
                char Buf[4];
                ZeroMemory(Buf, sizeof(Buf));
                AnsiString asSendData = "";
                asSendData = "![" + IntTo485Address(Addr) + "070" + IntToStr(cbScreen->ItemIndex);
                asSendData = asSendData + "!]";
                iSendSize = asSendData.Length();
                memcpy(SendData, asSendData.c_str(), iSendSize);
                if(ModuleASCIIData(ComKind, Addr, SendData, iSendSize, bMsg))
                {
                    if(bDataFail)
                        AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
                    else
                        AddLog(asMessage + " [OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
            }
            else
            {
                BYTE TempData[1];
                TempData[0] = DispOff;
                asMessage = "Display STOP";
                Connect = ModuleData(ComKind, Addr, TempData, 1, 0, DC, bMsg);
                if(Connect)
                {
                    if(bDataFail)
                        AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
                    else
                        AddLog(asMessage + " [OK]" + asLogMessage, clBlack);
                    if(Connect)
                    {
                        asMessage = LangFile.BtnScreen;
                        ModuleData(ComKind, Addr, ColorData, sizeof(ColorData), 0, (OPCode)tempOpCode, bMsg);
                        if(bDataFail)
                            AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
                        else
                            AddLog(asMessage + " [OK]" + asLogMessage, clBlack);
                    }
                }
                else
                    AddLog(asMessage + " [FAIL]" + asLogMessage, clRed);
            }
            ComClose(ComKind);
        }
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnSendSyncClick(TObject *Sender)
{
        // 신호 출력 전송 버튼
        bStopSend = false;
        BYTE SendData[28];
        int iSendSize = 4;
        ZeroMemory(SendData, sizeof(SendData));
        try{
            switch(cbSoundSec1->ItemIndex)
            {
                case 0:
                {
                    SendData[0] = 0x00;
                    SendData[1] = 0xf1;
                }
                break;
                case 1:
                {
                    SendData[0] = 0x00;
                    SendData[1] = 0xf0;
                }
                break;
                case 2:
                {
                    SendData[0] = 0x00;
                    SendData[1] = 0x00;
                }
                break;
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    SendData[0] = cbSoundSec1->ItemIndex - 2;
                break;
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    SendData[0] = 12 + (cbSoundSec1->ItemIndex - 13) * 2;
                break;
                case 18:
                    SendData[0] = 25;
                break;
                case 19:
                    SendData[0] = 30;
                break;
                case 20:
                    SendData[0] = 40;
                break;
                case 21:
                    SendData[0] = 50;
                break;
                case 22:
                    SendData[0] = 75;
                break;
                case 23:
                    SendData[0] = 100;
                break;
                case 24:
                    SendData[0] = 120;
                break;
                case 25:
                    SendData[0] = 140;
                break;
                case 26:
                    SendData[0] = 160;
                break;
                case 27:
                    SendData[0] = 180;
                break;
                case 28:
                    SendData[0] = 200;
                break;
                case 29:
                    SendData[0] = 220;
                break;
                case 30:
                    SendData[0] = 230;
                break;
                default:
                    SendData[0] = 1;
                break;
            }
            switch(cbSoundSec2->ItemIndex)
            {
                case 0:
                {
                    SendData[2] = 0x00;
                    SendData[3] = 0xf1;
                }
                break;
                case 1:
                {
                    SendData[2] = 0x00;
                    SendData[3] = 0xf0;
                }
                break;
                case 2:
                {
                    SendData[2] = 0x00;
                    SendData[3] = 0x00;
                }
                case 3:
                case 4:
                case 5:
                case 6:
                case 7:
                case 8:
                case 9:
                case 10:
                case 11:
                case 12:
                    SendData[2] = cbSoundSec2->ItemIndex - 2;
                break;
                case 13:
                case 14:
                case 15:
                case 16:
                case 17:
                    SendData[2] = 12 + (cbSoundSec2->ItemIndex - 13) * 2;
                break;
                case 18:
                    SendData[2] = 25;
                break;
                case 19:
                    SendData[2] = 30;
                break;
                case 20:
                    SendData[2] = 40;
                break;
                case 21:
                    SendData[2] = 50;
                break;
                case 22:
                    SendData[2] = 75;
                break;
                case 23:
                    SendData[2] = 100;
                break;
                case 24:
                    SendData[2] = 120;
                break;
                case 25:
                    SendData[2] = 140;
                break;
                case 26:
                    SendData[2] = 160;
                break;
                case 27:
                    SendData[2] = 180;
                break;
                case 28:
                    SendData[2] = 200;
                break;
                case 29:
                    SendData[2] = 220;
                break;
                case 30:
                    SendData[2] = 230;
                break;
                default:
                    SendData[2] = 1;
                break;
            }
            if(FormMain->bOptionJ4 == 7)
            {
                iSendSize = 8;
                switch(cbSoundSec3->ItemIndex)
                {
                    case 0:
                    {
                        SendData[4] = 0x00;
                        SendData[5] = 0xf1;
                    }
                    break;
                    case 1:
                    {
                        SendData[4] = 0x00;
                        SendData[5] = 0xf0;
                    }
                    break;
                    case 2:
                    {
                        SendData[4] = 0x00;
                        SendData[5] = 0x00;
                    }
                    break;
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                        SendData[4] = cbSoundSec3->ItemIndex - 2;
                    break;
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                        SendData[4] = 12 + (cbSoundSec3->ItemIndex - 13) * 2;
                    break;
                    case 18:
                        SendData[4] = 25;
                    break;
                    case 19:
                        SendData[4] = 30;
                    break;
                    case 20:
                        SendData[4] = 40;
                    break;
                    case 21:
                        SendData[4] = 50;
                    break;
                    case 22:
                        SendData[4] = 75;
                    break;
                    case 23:
                        SendData[4] = 100;
                    break;
                    case 24:
                        SendData[4] = 120;
                    break;
                    case 25:
                        SendData[4] = 140;
                    break;
                    case 26:
                        SendData[4] = 160;
                    break;
                    case 27:
                        SendData[4] = 180;
                    break;
                    case 28:
                        SendData[4] = 200;
                    break;
                    case 29:
                        SendData[4] = 220;
                    break;
                    case 30:
                        SendData[4] = 230;
                    break;
                    default:
                        SendData[4] = 1;
                    break;
                }
                switch(cbSoundSec4->ItemIndex)
                {
                    case 0:
                    {
                        SendData[6] = 0x00;
                        SendData[7] = 0xf1;
                    }
                    break;
                    case 1:
                    {
                        SendData[6] = 0x00;
                        SendData[7] = 0xf0;
                    }
                    break;
                    case 2:
                    {
                        SendData[6] = 0x00;
                        SendData[7] = 0x00;
                    }
                    case 3:
                    case 4:
                    case 5:
                    case 6:
                    case 7:
                    case 8:
                    case 9:
                    case 10:
                    case 11:
                    case 12:
                        SendData[6] = cbSoundSec4->ItemIndex - 2;
                    break;
                    case 13:
                    case 14:
                    case 15:
                    case 16:
                    case 17:
                        SendData[6] = 12 + (cbSoundSec4->ItemIndex - 13) * 2;
                    break;
                    case 18:
                        SendData[6] = 25;
                    break;
                    case 19:
                        SendData[6] = 30;
                    break;
                    case 20:
                        SendData[6] = 40;
                    break;
                    case 21:
                        SendData[6] = 50;
                    break;
                    case 22:
                        SendData[6] = 75;
                    break;
                    case 23:
                        SendData[6] = 100;
                    break;
                    case 24:
                        SendData[6] = 120;
                    break;
                    case 25:
                        SendData[6] = 140;
                    break;
                    case 26:
                        SendData[6] = 160;
                    break;
                    case 27:
                        SendData[6] = 180;
                    break;
                    case 28:
                        SendData[6] = 200;
                    break;
                    case 29:
                        SendData[6] = 220;
                    break;
                    case 30:
                        SendData[6] = 230;
                    break;
                    default:
                        SendData[6] = 1;
                    break;
                }
            }
        }catch(...){
            SendData[0] = 1;
            SendData[2] = 1;
            SendData[4] = 1;
            SendData[6] = 1;
        }
        ButtonEnabled(false);
        int Addr = 0;
        if(bRS485)
            Addr = Controler1;
        try{
            if(ComPortSet(ComKind, PortUse1, BaudRateUse1, Disable, Disable, IPAddress, IPPort, false))
            {
                if(PorotocolType == 1)
                {
                    AnsiString asSendData = "![" + IntTo485Address(Addr) + "022";
                    char Buf[6];
                    ZeroMemory(Buf, sizeof(Buf));
                    if(SendData[1] == 0xf1)
                        asSendData = asSendData + "61696";
                    else if(SendData[1] == 0xf0)
                        asSendData = asSendData + "61440";
                    else
                    {
                        wsprintf(Buf, "%05d", SendData[0]);
                        asSendData = asSendData + AnsiString(Buf);
                    }
                    ZeroMemory(Buf, sizeof(Buf));
                    if(SendData[3] == 0xf1)
                        asSendData = asSendData + "61696";
                    else if(SendData[3] == 0xf0)
                        asSendData = asSendData + "61440";
                    else
                    {
                        wsprintf(Buf, "%05d", SendData[2]);
                        asSendData = asSendData + AnsiString(Buf);
                    }
                    if(FormMain->bOptionJ4 == 7)
                    {
                        if(SendData[5] == 0xf1)
                            asSendData = asSendData + "61696";
                        else if(SendData[5] == 0xf0)
                            asSendData = asSendData + "61440";
                        else
                        {
                            wsprintf(Buf, "%05d", SendData[4]);
                            asSendData = asSendData + AnsiString(Buf);
                        }
                        ZeroMemory(Buf, sizeof(Buf));
                        if(SendData[7] == 0xf1)
                            asSendData = asSendData + "61696";
                        else if(SendData[7] == 0xf0)
                            asSendData = asSendData + "61440";
                        else
                        {
                            wsprintf(Buf, "%05d", SendData[6]);
                            asSendData = asSendData + AnsiString(Buf);
                        }
                    }
                    asSendData = asSendData + "!]";
                    iSendSize = asSendData.Length();
                    memcpy(SendData, asSendData.c_str(), iSendSize);
                    if(ModuleASCIIData(ComKind, Addr, SendData, iSendSize, bMsg))
                    {
                        if(bDataFail)
                            AddLog(LangFile.BtnSignalSend + " [Fail] " + asLogMessage, clRed);
                        else
                            AddLog(LangFile.BtnSignalSend + " [OK] " + asLogMessage, clBlack);
                    }
                    else
                        AddLog(LangFile.BtnSignalSend + " [Fail] " + asLogMessage, clRed);
                }
                else
                {
                    if(ModuleData(ComKind, Addr, SendData, iSendSize, 0, SO, bMsg))
                    {
                        if(bDataFail)
                            AddLog(LangFile.BtnSignalSend + " [Fail] " + asLogMessage, clRed);
                        else
                            AddLog(LangFile.BtnSignalSend + " [OK] " + asLogMessage, clBlack);
                    }
                    else
                        AddLog(LangFile.BtnSignalSend + " [Fail] " + asLogMessage, clRed);
                }
                ComClose(ComKind);
            }
        }catch(...){
        }
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnBrightClick(TObject *Sender)
{
        // 전광판 밝기 전송 버튼
        bStopSend = false;
        BYTE SendData[10];
        ZeroMemory(SendData, sizeof(SendData));
        int BValue = 100;
        if(cbBright->ItemIndex == 1)
            BValue = 75;
        else if(cbBright->ItemIndex == 2)
            BValue = 50;
        else if(cbBright->ItemIndex == 3)
            BValue = 25;
        else if(cbBright->ItemIndex == 4)
            BValue = 5;
        try{
            SendData[0] = BValue;
        }catch(...){
        }
        int iSendSize = 1;
        ButtonEnabled(false);
        int Addr = 0;
	if(bRS485)
            Addr = Controler1;
        if(ComPortSet(ComKind, PortUse1, BaudRateUse1, Disable, Disable, IPAddress, IPPort, false))
        {
            if(PorotocolType == 1)
            {
                AnsiString asSendData = "![" + IntTo485Address(Addr) + "050";
                if(cbBright->ItemIndex == 1)
                    asSendData = asSendData + "75!]";
                else if(cbBright->ItemIndex == 2)
                    asSendData = asSendData + "50!]";
                else if(cbBright->ItemIndex == 3)
                    asSendData = asSendData + "25!]";
                else if(cbBright->ItemIndex == 4)
                    asSendData = asSendData + "05!]";
                else
                    asSendData = asSendData + "99!]";
                iSendSize = asSendData.Length();
                memcpy(SendData, asSendData.c_str(), iSendSize);
                if(ModuleASCIIData(ComKind, Addr, SendData, iSendSize, bMsg))
                {
                    if(bDataFail)
                        AddLog(LangFile.FormProtocolLabel33 +  "[FAIL]" + asLogMessage, clRed);
                    else
                        AddLog(LangFile.FormProtocolLabel33 +  "[OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(LangFile.FormProtocolLabel33 +  "[FAIL]" + asLogMessage, clRed);
            }
            else
            {
                if(ModuleData(ComKind, Addr, SendData, iSendSize, 0, BC, bMsg))
                {
                    if(bDataFail)
                        AddLog(LangFile.FormProtocolLabel33 +  "[FAIL]" + asLogMessage, clRed);
                    else
                        AddLog(LangFile.FormProtocolLabel33 +  "[OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(LangFile.FormProtocolLabel33 +  "[FAIL]" + asLogMessage, clRed);
            }
            ComClose(ComKind);
        }
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnRealTimeClick(TObject *Sender)
{
        bStopSend = false;
        BYTE SendData[10];
        ZeroMemory(SendData, sizeof(SendData));
        int iSendSize = 1;
        ButtonEnabled(false);
        int Addr = 0;
	if(bRS485)
            Addr = Controler1;
        if(ComPortSet(ComKind, PortUse1, BaudRateUse1, Disable, Disable, IPAddress, IPPort, false))
        {
            //if(PorotocolType == 1)
            //{
                AnsiString asSendData = "![" + IntTo485Address(Addr) + "062";
                if(cbRealTime->ItemIndex == 1)
                    asSendData = asSendData + "Y!]";
                else// if(cbRealTime->ItemIndex == 0)
                    asSendData = asSendData + "N!]";
                //else
                //    asSendData = asSendData + "1!]";
                bRealTimeIndex = cbRealTime->ItemIndex;
                iSendSize = asSendData.Length();
                memcpy(SendData, asSendData.c_str(), iSendSize);
                if(ModuleASCIIData(ComKind, Addr, SendData, iSendSize, bMsg))
                {
                    if(bDataFail)
                        AddLog(LangFile.FormProtocolLabel28 +  "[FAIL]" + asLogMessage, clRed);
                    else
                        AddLog(LangFile.FormProtocolLabel28 +  "[OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(LangFile.FormProtocolLabel28 +  "[FAIL]" + asLogMessage, clRed);
            /*}
            else
            {
                if(ModuleData(ComKind, Addr, SendData, iSendSize, 0, BC, bMsg))
                {
                    if(bDataFail)
                        AddLog(LangFile.FormProtocolLabel33 +  "[FAIL]" + asLogMessage, clRed);
                    else
                        AddLog(LangFile.FormProtocolLabel33 +  "[OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(LangFile.FormProtocolLabel33 +  "[FAIL]" + asLogMessage, clRed);
            }*/
            ComClose(ComKind);
        }
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BitBtnPowerLEDClick(TObject *Sender)
{
        //전광판 전원 전송 버튼
        bStopSend = false;
        int Addr = 0;
        ButtonEnabled(false);
        if(bRS485)
            Addr = Controler1;
        OPCode tempOPC;
        BYTE SendData[9];
        int iSendSize = 1;
        ZeroMemory(SendData, sizeof(SendData));
        if(Sender == BitBtnPowerLEDOff)
            SendData[0] = PowerOff;
        else
            SendData[0] = PowerOn;
        if(ComPortSet(ComKind, PortUse1, BaudRateUse1, Disable, Disable, IPAddress, IPPort, false))
        {
            if(PorotocolType == 1)
            {
                AnsiString asSendData = "![" + IntTo485Address(Addr) + "021";
                char Buf[3];
                ZeroMemory(Buf, sizeof(Buf));
                wsprintf(Buf, "%01d", SendData[0]);
                asSendData = asSendData + AnsiString(Buf) + "!]";
                iSendSize = asSendData.Length();
                memcpy(SendData, asSendData.c_str(), iSendSize);
                if(ModuleASCIIData(ComKind, Addr, SendData, iSendSize, bMsg))
                {
                    if(bDataFail)
                    {
                        if(Sender == BitBtnPowerLEDOff)
                            AddLog(LangFile.Message04 + " [Fail]" + asLogMessage, clRed);
                        else
                            AddLog(LangFile.Message03 + " [Fail]" + asLogMessage, clRed);
                    }
                    else
                    {
                        if(Sender == BitBtnPowerLEDOff)
                            AddLog(LangFile.Message04 + " [OK]" + asLogMessage, clBlack);
                        else
                            AddLog(LangFile.Message03 + " [OK]" + asLogMessage, clBlack);
                    }
                }
                else
                {
                    if(Sender == BitBtnPowerLEDOff)
                        AddLog(LangFile.Message04 + " [Fail]" + asLogMessage, clRed);
                    else
                        AddLog(LangFile.Message03 + " [Fail]" + asLogMessage, clRed);
                }
            }
            else
            {
                if(ModuleData(ComKind, Addr, SendData, iSendSize, 0, PC, bMsg))
                {
                    if(bDataFail)
                    {
                        if(Sender == BitBtnPowerLEDOff)
                            AddLog(LangFile.Message04 + " [Fail]" + asLogMessage, clRed);
                        else
                            AddLog(LangFile.Message03 + " [Fail]" + asLogMessage, clRed);
                    }
                    else
                    {
                        if(Sender == BitBtnPowerLEDOff)
                            AddLog(LangFile.Message04 + " [OK]" + asLogMessage, clBlack);
                        else
                            AddLog(LangFile.Message03 + " [OK]" + asLogMessage, clBlack);
                    }
                }
                else
                {
                    if(Sender == BitBtnPowerLEDOff)
                        AddLog(LangFile.Message04 + " [Fail]" + asLogMessage, clRed);
                    else
                        AddLog(LangFile.Message03 + " [Fail]" + asLogMessage, clRed);
                }
            }
            ComClose(ComKind);
        }
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnTimeReadClick(TObject *Sender)
{
        //시간읽기 전송 버튼
        bStopSend = false;
        int Addr = 0;
        ButtonEnabled(false);
        if(bRS485)
            Addr = Controler1;
        BYTE SendData[8];
        int iSendSize = 1;
        ZeroMemory(SendData, sizeof(SendData));
        if(ComPortSet(ComKind, PortUse1, BaudRateUse1, Disable, Disable, IPAddress, IPPort, false))
        {
            if(PorotocolType == 1)
            {
                AnsiString asSendData = "![" + IntTo485Address(Addr) + "031!]";
                iSendSize = asSendData.Length();
                memcpy(SendData, asSendData.c_str(), iSendSize);
                if(ModuleASCIIData(ComKind, Addr, SendData, iSendSize, bMsg))
                {
                    if(bDataFail)
                        AddLog(LangFile.BtnTimeRead + " [Fail]" + asLogMessage, clRed);
                    else
                        AddLog(LangFile.BtnTimeRead + " [OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(LangFile.BtnTimeRead + " [Fail]" + asLogMessage, clRed);
            }
            else
            {
                if(ModuleData(ComKind, Addr, SendData, 0, 0, RCT, bMsg))
                {
                    if(bDataFail)
                        AddLog(LangFile.BtnTimeRead + " [Fail]" + asLogMessage, clRed);
                    else
                        AddLog(LangFile.BtnTimeRead + " [OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(LangFile.BtnTimeRead + " [Fail]" + asLogMessage, clRed);
            }
            ComClose(ComKind);
        }
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnTimeSetClick(TObject *Sender)
{
        ButtonEnabled(false);
        CurrenTimeSync();
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnFResetClick(TObject *Sender)
{
        // 전광판 공장 초기화 버튼
        bStopSend = false;
        BYTE SendData[8];
        ZeroMemory(SendData, sizeof(SendData));
        if(cbProgramType->ItemIndex == 0)
            ProgramType = e2BPP;
        else if(cbProgramType->ItemIndex == 2)
            ProgramType = e8BPP;
        else if(cbProgramType->ItemIndex == 3)
            ProgramType = e24BPP;
        else //if(cbProgramType->ItemIndex == 3)
            ProgramType = e3BPP;
        SendData[0] = ProgramType;
        SendData[1] = UpDownHeight->Position;
        SendData[2] = UpDownWidth->Position;
        int iSendSize = 3;
        ButtonEnabled(false);
        int Addr = 0;
	if(bRS485)
            Addr = Controler1;
        AnsiString asMsg = "";
        if(Sender == BtnFReset)
           asMsg = LangFile.BtnFReset;
        else
           asMsg = LangFile.BtnReset;
        if(ComPortSet(ComKind, PortUse1, BaudRateUse1, Disable, Disable, IPAddress, IPPort, false))
        {
            if(PorotocolType == 1)
            {
                AnsiString asSendData = "";
                if(Sender == BtnFReset)
                    asSendData = "![" + IntTo485Address(Addr) + "042!]";
                else
                    asSendData = "![" + IntTo485Address(Addr) + "041!]";
                iSendSize = asSendData.Length();
                memcpy(SendData, asSendData.c_str(), iSendSize);
                if(ModuleASCIIData(ComKind, Addr, SendData, iSendSize, bMsg))
                {
                    if(bDataFail)
                        AddLog(asMsg + " (" + IntToStr(UpDownHeight->Position) + " X " + IntToStr(UpDownWidth->Position) + ") [FAIL]" + asLogMessage, clRed);
                    else
                        AddLog(asMsg + " (" + IntToStr(UpDownHeight->Position) + " X " + IntToStr(UpDownWidth->Position) + ") [OK]" + asLogMessage, clBlack);
                }
                else
                    AddLog(asMsg + " (" + IntToStr(UpDownHeight->Position) + " X " + IntToStr(UpDownWidth->Position) + ") [FAIL]" + asLogMessage, clRed);
            }
            else
            {
                BYTE bOPCode;
                if(Sender == BtnFReset)
                {
                    iSendSize = 3;
                    bOPCode = DFS;
                }
                else
                {
                   iSendSize = 1;
                   bOPCode = DS;
                   SendData[0] = 0x00;
                }
                if(ModuleData(ComKind, Addr, SendData, iSendSize, 0, bOPCode, bMsg))
                    AddLog(asMsg + " [OK]" + asLogMessage, clBlack);
                else
                    AddLog(asMsg + " [FAIL]" + asLogMessage, clRed);
            }
            ComClose(ComKind);
        }
        ButtonEnabled(true);
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::cbLanguageClick(TObject *Sender)
{
        iLanguage = cbLanguage->ItemIndex;
        RoadLangFile(cbLanguage->Text);
        InputLang();
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::laHelpClick(TObject *Sender)
{
        AnsiString asLink = "http://www.dabitsol.com/files/DabitSimulator_UserManual_v9.0.pdf";
        try{
            ShellExecute(0, "open", asLink.c_str(), "", "", SW_SHOWNORMAL);
        }catch(...){
        }
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::laHelpMouseDown(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
        laHelp->Font->Style = TFontStyles() << fsItalic << fsUnderline << fsBold;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::laHelpMouseUp(TObject *Sender,
      TMouseButton Button, TShiftState Shift, int X, int Y)
{
        laHelp->Font->Style = TFontStyles() << fsUnderline << fsBold;
}
//--------------------------------------------------------------------------

void __fastcall TFormMain::cbRealTimeClick(TObject *Sender)
{
        bRealTimeIndex = cbRealTime->ItemIndex;
}
//---------------------------------------------------------------------------

void __fastcall TFormMain::BtnPacketReadClick(TObject *Sender)
{
        TFileStream *File = NULL;
        BYTE *PacketData = NULL;
        int iDataSize = 0;
        int iPos = 0;
        AnsiString asFirmFileData = "";
        AnsiString asMsg = "";
        try{
            OpenDialog->Options.Clear();
            OpenDialog->Files->Clear();
            OpenDialog->Options << ofFileMustExist << ofNoChangeDir;
            OpenDialog->FilterIndex = 1;
            OpenDialog->Filter = "TEXT File (*.txt)|*.txt|TEXT UTF8 File (*.txt)|*.txt|All files (*.*)|*.*";
            OpenDialog->DefaultExt = "txt";
            OpenDialog->Title = "Open Packet File";
            OpenDialog->FileName = "";
            OpenDialog->InitialDir = BasePath;
            if(OpenDialog->Execute())
            {
                BtnPacketRead->Enabled = false;
                File = new TFileStream(OpenDialog->FileName, fmOpenRead | fmShareCompat);
                if(!File->Size)
                {
                    if(File)
                    {
                        delete File;
                        File = NULL;
                    }
                    asMsg = ExtractFileName(OpenDialog->FileName) + " " + LangFile.Message15;
                    return;
                }
                iDataSize = File->Size;
                PacketData = new BYTE[iDataSize];
                ZeroMemory(PacketData, iDataSize);
                File->Read(PacketData, iDataSize);
                if(iDataSize > 0)
                    PacketParsing(PacketData, iDataSize);
            }
        }catch(...){
            asMsg = ExtractFileName(OpenDialog->FileName) + " " + LangFile.Message15;
            Message("\r\n" + asMsg + "\r\n", FormMain->LangFile.MessageButton04, "", "", asMsg.Length() - 2, 3, 1, clRed);
        }
        if(File)
        {
            delete File;
            File = NULL;
        }
        if(PacketData)
        {
            delete [] PacketData;
            PacketData = NULL;
        }
        BtnPacketRead->Enabled = true;
}
//---------------------------------------------------------------------------

bool __fastcall TFormMain::PacketParsing(BYTE * ParsingData, int iDataSize)
{
        WideString wParsingData = "";
        if(OpenDialog->FilterIndex == 2)
            wParsingData = UTF8ToANSI((char *)ParsingData, iDataSize);
        else
            wParsingData = WideString((char *)ParsingData);
        WideString SpareData = wParsingData;
        WideString wsChar = "";
        int iASCIIPos = 0;
        int bytePos = 0;
        int iDatalen = wParsingData.Length();
        bool bText = false;
        TTntRichEdit *tMemo;
        for(int i = 0; i < 10; i++)
        {
            try{
                tMemo = dynamic_cast<TTntRichEdit*>(FindComponent("mASCIIText" + IntToStr(i + 1)));
                if(tMemo)
                    tMemo->Text = "";
            }catch(...){
                return false;
            }
        }
        for(int j = 0; j < iDatalen; j++)
        {
            try{
                bytePos = wParsingData.Pos("/*");
                if(bytePos)
                {
                    wParsingData = wParsingData.SubString(bytePos + 4, wParsingData.Length() - 4);
                    bytePos = wParsingData.Pos("*/");
                    if(bytePos)
                    {
                        SpareData = wParsingData.SubString(wParsingData.Pos("!["), bytePos - wParsingData.Pos("!["));
                        wParsingData = wParsingData.SubString(bytePos, wParsingData.Length() - bytePos + 1);
                        iDatalen = wParsingData.Length();
                        bText = false;
                        for(int i = 0; i < SpareData.Length(); i++)
                        {
                            bytePos = SpareData.Pos("\r\n");
                            if(bytePos)
                            {
                                bText = true;
                                SpareData.Delete(bytePos, 2);
                            }
                            else
                                break;
                        }
                        if(bText)
                        {
                            try{
                                tMemo = dynamic_cast<TTntRichEdit*>(FindComponent("mASCIIText" + IntToStr(iASCIIPos + 1)));
                                if(tMemo)
                                {
                                    tMemo->Text = SpareData;
                                    iASCIIPos++;
                                }
                            }catch(...){
                                return false;
                            }
                        }
                        if(iASCIIPos >= 10)
                            break;
                    }
                    else
                        break;
                }
                else
                    break;
            }catch(...){
            }
        }
        return true;
}
//---------------------------------------------------------------------------

WideString __fastcall TFormMain:: UTF8ToANSI(const char *pszCode, int &length)
{
 
        WideString wsString;
        BSTR    bstrWide;
        //char*   pszAnsi;
        int     nLength;
        nLength = MultiByteToWideChar(CP_UTF8, 0, pszCode, lstrlenA(pszCode) + 1, NULL, NULL);
        bstrWide = SysAllocStringLen(NULL, nLength);

        MultiByteToWideChar(CP_UTF8, 0, pszCode, lstrlenA(pszCode) + 1, bstrWide, nLength);
        //nLength = WideCharToMultiByte(CP_ACP, 0, bstrWide, -1, NULL, 0, NULL, NULL);
        //pszAnsi = new char[nLength];
        //WideCharToMultiByte(CP_ACP, 0, bstrWide, -1, pszAnsi, nLength, NULL, NULL);
        wsString = bstrWide;
        //SysFreeString(bstrWide);
        length = nLength;
        return wsString;
}
//---------------------------------------------------------------------------

AnsiString __fastcall TFormMain::Int2HexConvert(BYTE Value)
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

