//---------------------------------------------------------------------------

#ifndef TFormDABITNetH
#define TFormDABITNetH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "te_controls.hpp"
#include "TntButtons.hpp"
#include "TntExtCtrls.hpp"
#include "TntStdCtrls.hpp"
#include <Buttons.hpp>
#include <IdBaseComponent.hpp>
#include <IdComponent.hpp>
#include <IdUDPBase.hpp>
#include <IdUDPClient.hpp>
#include <IdUDPServer.hpp>
#include <AppEvnts.hpp>
#include "Protocol.h"
#include "TFormComPort.h"
#include <IdAntiFreeze.hpp>
#include <IdAntiFreezeBase.hpp>
#include <IdDNSResolver.hpp>
#include <IdIcmpClient.hpp>
#include <IdRawBase.hpp>
#include <IdRawClient.hpp>
#include <ScktComp.hpp>
#include "CPort.hpp"
#include "MMTimer.hpp"
#include "TntComCtrls.hpp"
#include <ComCtrls.hpp>
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------
class TFormDABITNet : public TForm
{
__published:	// IDE-managed Components
        TIdUDPClient *IdUDPClient;
        TIdUDPServer *IdUDPServer;
        TTeEdit *eMACAddr;
        TTntLabel *TntLabel1;
        TTntLabel *TntLabel2;
        TApplicationEvents *ApplicationEvents1;
        TIdDNSResolver *IdDNSResolver;
        TIdIcmpClient *IdIcmpClient1;
        TClientSocket *ClientSocket1;
        TIdAntiFreeze *IdAntiFreeze1;
        TTntComboBox *cbDirectIPAddr;
        TTntBitBtn *BtnAllReset;
        TTntLabel *TntLabel10;
        TTeEdit *eDirectPort;
        TComPort *ComPort1;
        TMMTimer *SerialMMTimer;
        TTntLabel *laComKind;
        TTntPanel *pnMainDivide;
        TTntPanel *pnMain;
        TTntPanel *pnDivide;
        TTntPanel *pnCaption;
        TTntPanel *pnBorderIcon;
        TTntSpeedButton *btnHide;
        TTntSpeedButton *btnMax;
        TTntSpeedButton *btnExit;
        TTntPanel *pnMainBar;
        TTntPanel *pnNetwork;
        TTntSpeedButton *tsNetwork;
        TTntListBox *mMACAddr;
        TTntCheckBox *cbDirectIP;
        TTeButton *btnSetup;
        TTntPanel *tnNetwork;
        TTntPanel *tnWiFi;
        TTntLabel *TntLabel9;
        TTntEdit *eMemo;
        TTntPanel *gbLAN;
        TTntPanel *pnLAN;
        TTntRadioButton *rbEthernetSet;
        TTntPanel *pnBtnSearching;
        TTntSpeedButton *BtnSearching;
        TTntPanel *pnBtnSetting;
        TTntSpeedButton *BtnSetting;
        TTntPanel *pnBtnReset;
        TTntSpeedButton *BtnReset;
        TTntPanel *pnBtnClose;
        TTntSpeedButton *BtnClose;
        TTntCheckBox *cbWiFiSet;
        TTntRadioButton *rbWiFiSet;
        TTntPanel *gbWiFiModeSetting;
        TTntPanel *pnWiFiModeSetting;
        TTntPanel *pnOption;
        TTntSpeedButton *tsOption;
        TTntPanel *pnComKind;
        TTntPanel *laLAN;
        TTntPanel *pnBtnAdd;
        TTntSpeedButton *BtnAdd;
        TTntPanel *laWiFiSet;
        TTntPanel *laMainInterface;
        TTntPanel *gbMainInterface;
        TTntPanel *pnMainInterface;
        TTntRadioButton *rbBluetooth;
        TTntRadioButton *rbWiFi;
        TTntPanel *laSubInterface;
        TTntPanel *gbSubInterface;
        TTntPanel *pnSubInterface;
        TTntRadioButton *rbTTL;
        TTntRadioButton *rbRS232;
        TTntRadioButton *rbEthernet;
        TTntPanel *pnBoardList;
        TTntSpeedButton *tsBoardList;
        TTntLabel *TntLabel7;
        TTntLabel *TntLabel5;
        TTntLabel *TntLabel4;
        TTntPanel *rgOperationMode;
        TTntPanel *pnOperationMode;
        TTntRadioButton *rbServer;
        TTntRadioButton *rbClient;
        TTntPanel *rgComMode;
        TTntPanel *pnComMode;
        TTntRadioButton *rgStatic;
        TTntRadioButton *rgDHCP;
        TTntPanel *laServer;
        TTntPanel *laOperationMode;
        TTntPanel *laComMode;
        TTntPanel *gbServer;
        TTntPanel *pnServer;
        TTntLabel *laServerIP;
        TTntLabel *laServerDDNS;
        TTntLabel *TntLabel8;
        TTntPanel *pnBtnConnect;
        TTntSpeedButton *BtnConnect;
        TTntRadioButton *rbServerIP;
        TTntRadioButton *rbServerName;
        TTntEdit *eServerPort;
        TTntEdit *eServerName;
        TTntEdit *eServerIPAddr;
        TTntEdit *eSubnetMask;
        TTntEdit *ePort;
        TTntEdit *eIPAddr;
        TTntEdit *eGateway;
        TTntLabel *TntLabel3;
        TTntLabel *TntLabel11;
        TTntEdit *eInterval;
        TTntLabel *TntLabel12;
        TTntPanel *pnWiFi;
        TTntSpeedButton *tsWiFi;
        TTntPanel *tnOption;
        TTntPanel *laComMethod;
        TTntRadioButton *rbUDP;
        TTntPanel *gbUDP;
        TTntPanel *pnUDP;
        TTntLabel *Label15;
        TTntLabel *Label16;
        TTntEdit *Edit8;
        TTntEdit *Edit7;
        TTntRadioButton *rbSerial;
        TTntPanel *gbSerial;
        TTntPanel *pnSerial;
        TTeLabel *Label4;
        TTeLabel *Label3;
        TTeLabel *Label12;
        TTntLabel *Label1;
        TTntLabel *Label2;
        TTeComboBox *BaudBox1;
        TTeComboBox *ComBox1;
        TTntComboBox *ComBox2;
        TTeComboBox *cbRTS;
        TTntCheckBox *cbRS485;
        TTeComboBox *cbDTR;
        TTeButton *BPortCon;
        TTeButton *BPortClose;
        TTntComboBox *BaudBox2;
        TTntPanel *pnBtnSystem;
        TTntSpeedButton *BtnSystem;
        TTntPanel *pnBtnSave;
        TTntSpeedButton *BtnSave;
        TTntLabel *TntLabel6;
        TTntPanel *gbAPMode;
        TTntPanel *pnAPMode;
        TTntRadioButton *rbSTA;
        TTntRadioButton *rbAP;
        TTntLabel *TntLabel14;
        TTntEdit *eSSID;
        TTntEdit *eSSIDPW;
        TTntLabel *TntLabel13;
        TTntLabel *TntLabel15;
        TTntPanel *gbUDPMode;
        TTntPanel *pnUDPMode;
        TTntRadioButton *rbOnlyEth;
        TTntRadioButton *rbBoth;
        TTntRadioButton *rbOnlyWiFi;
        TTntRadioButton *rbEth;
        TTntLabel *laHelp;
        TTntPanel *pnICON;
        TTntSpeedButton *TntSpeedButton1;
        TTntSpeedButton *btnHelp;
        TTntEdit *etMACAddr;
        TTntEdit *eDirectIPAddr;
        TTntPanel *pnInfor;
        TTntSpeedButton *tsInfor;
        TTntPanel *tnInfor;
        TTntPanel *pnlaFirmwareInfor;
        TTntPanel *gbFirmwareInfor;
        TTntPanel *pnFirmwareInfor;
        TTntLabel *laFirmwareInfor;
        TTntPanel *pnBtnFirmwareRead;
        TTntSpeedButton *BtnFirmwareRead;
        TTntPanel *pnBtnFirmwareSet;
        TTntSpeedButton *BtnFirmwareSet;
        TTntPanel *gbSettingInfor;
        TTntPanel *pnSettingInfor;
        TTntLabel *laDeviceConnectPort;
        TTntLabel *laDebuggingState;
        TTntComboBox *cbDeviceConnectPort;
        TTntComboBox *cbDeviceBaudrate;
        TTntPanel *gbModeInfor;
        TTntPanel *pnModeInfor;
        TTntCheckBox *cbEthernet;
        TTntCheckBox *cbWiFi;
        TTntCheckBox *cbBluetooth;
        TTntPanel *pnlaSettingInfor;
        TTntPanel *pnlaModeInfor;
        TTntLabel *laDeviceBaudrate;
        TTntComboBox *cbDebuggingState;
        TTntPanel *pnDivide2;
        TTntLabel *laComm;
        TTntPanel *pnlaDataPacking;
        TTntPanel *gbDataPacking;
        TTntPanel *pnDataPacking;
        TTntLabel *laTimeOut;
        TTntLabel *laTimeOut2;
        TTntLabel *laEndTextHex2;
        TTntLabel *laEndTextHex1;
        TTntLabel *laEndTextASCII1;
        TTntLabel *laEndTextASCII2;
        TTntEdit *eTimeOut;
        TTntEdit *eEndTextHex2;
        TTntEdit *eEndTextHex1;
        TTntEdit *eEndTextASCII1;
        TTntEdit *eEndTextASCII2;
        TTntCheckBox *cbUDPUnicast;
        TTntLabel *laMACAddr;
        TTntPanel *laUDPType;
        TTntPanel *pnBtnNetworkAdapter;
        TTntSpeedButton *BtnNetworkAdapter;
        TTntPanel *pnBtnDB300Info;
        TTntSpeedButton *BtnDB300Info;
        TTntPanel *laAdvanced;
        TTntPanel *gbAdvanced;
        TTntPanel *pnAdvanced;
        TTntLabel *laSearchTarget;
        TTntComboBox *cbSearchTarget;
        TTntComboBox *cbResponseType;
        TTntLabel *laResponseType;
        TTntLabel *laDB300Tab;
        TTntComboBox *cbDB300Tab;
        TTntPanel *pnBtnBLEStop;
        TTntSpeedButton *BtnBLEStop;
        TTntPanel *pnBtnDeaultIP;
        TTntSpeedButton *BtnDeaultIP;
        void __fastcall IdUDPServerUDPRead(TObject *Sender,
          TStream *AData, TIdSocketHandle *ABinding);
        void __fastcall BtnSearchingClick(TObject *Sender);
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall BtnCloseClick(TObject *Sender);
        void __fastcall btnSettingClick(TObject *Sender);
        void __fastcall mMACAddrClick(TObject *Sender);
        void __fastcall ApplicationEvents1ShortCut(TWMKey &Msg,
          bool &Handled);
        void __fastcall rgComModeClick(TObject *Sender);
        void __fastcall mMACAddrDrawItem(TWinControl *Control, int Index,
          TRect &Rect, TOwnerDrawState State);
        void __fastcall eMemoKeyPress(TObject *Sender, char &Key);
        void __fastcall BtnResetClick(TObject *Sender);
        void __fastcall rgOperationModeClick(TObject *Sender);
        void __fastcall cbDirectIPClick(TObject *Sender);
        void __fastcall FormCloseQuery(TObject *Sender, bool &CanClose);
        void __fastcall IdIcmpClient1Reply(TComponent *ASender,
          const TReplyStatus &AReplyStatus);
        void __fastcall ClientSocket1Connect(TObject *Sender,
          TCustomWinSocket *Socket);
        void __fastcall ClientSocket1Disconnect(TObject *Sender,
          TCustomWinSocket *Socket);
        void __fastcall ClientSocket1Error(TObject *Sender,
          TCustomWinSocket *Socket, TErrorEvent ErrorEvent,
          int &ErrorCode);
        void __fastcall ClientSocket1Read(TObject *Sender,
          TCustomWinSocket *Socket);
        void __fastcall BtnConnectClick(TObject *Sender);
        void __fastcall rbServerIPClick(TObject *Sender);
        void __fastcall rbServerNameClick(TObject *Sender);
        void __fastcall rbEthernetSetClick(TObject *Sender);
        void __fastcall btnSetupClick(TObject *Sender);
        void __fastcall ComPort1RxChar(TObject *Sender, int Count);
        void __fastcall SerialMMTimerTimer(TObject *Sender);
        void __fastcall BtnSaveClick(TObject *Sender);
        void __fastcall BtnSystemClick(TObject *Sender);
        void __fastcall ComBox1ListBoxClick(TObject *Sender);
        void __fastcall ComBox1DropDown(TObject *Sender);
        void __fastcall BPortConClick(TObject *Sender);
        void __fastcall rbUDPClick(TObject *Sender);
        void __fastcall BPortCloseClick(TObject *Sender);
        void __fastcall tsNetworkClick(TObject *Sender);
        void __fastcall pnCaptionMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall btnHideClick(TObject *Sender);
        void __fastcall btnMaxClick(TObject *Sender);
        void __fastcall btnExitClick(TObject *Sender);
        void __fastcall BtnAddClick(TObject *Sender);
        void __fastcall rbAPModeClick(TObject *Sender);
        void __fastcall pnCaptionMouseMove(TObject *Sender,
          TShiftState Shift, int X, int Y);
        void __fastcall rgUDPModeClick(TObject *Sender);
        void __fastcall BaudBox2Click(TObject *Sender);
        void __fastcall ComBox2Click(TObject *Sender);
        void __fastcall laHelpClick(TObject *Sender);
        void __fastcall laHelpMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall laHelpMouseUp(TObject *Sender, TMouseButton Button,
          TShiftState Shift, int X, int Y);
        void __fastcall btnHelpClick(TObject *Sender);
        void __fastcall BtnFirmwareReadClick(TObject *Sender);
        void __fastcall BtnFirmwareSetClick(TObject *Sender);
        void __fastcall cbUDPUnicastClick(TObject *Sender);
        void __fastcall BtnNetworkAdapterClick(TObject *Sender);
        void __fastcall BtnDB300InfoClick(TObject *Sender);
        void __fastcall cbSearchTypeClick(TObject *Sender);
        void __fastcall cbResponseTypeClick(TObject *Sender);
        void __fastcall cbDB300TabClick(TObject *Sender);
        void __fastcall BtnBLEStopClick(TObject *Sender);
        void __fastcall BtnDeaultIPClick(TObject *Sender);
private:	// User declarations
        TFormComPort *FComPort;
        AnsiString MACIPData[255];
        int MACCnt;
        AnsiString VerMj[4];
        BYTE bAddKind;
        //int Controler1;
        //AnsiString    SIPAddress;
        int SIPPort;
        //bool bDDNS;
        //bool bFlagReplyPacket;
        //bool FlagCrcPacket;
        //bool FlagDlePacket;
        //int WaitTime;
        //int DelayTime;
        //int ServerCommOpenDelay;
        bool SchBmpFile;
        //bool DelayRun;
        bool bCRCFail;
        AnsiString asLogMessage;
        AnsiString Status;
        Response RP;
        bool bDLECheck;
        int OldXPos;
        int OldYPos;
        bool bCaptionMove;
        int UDPType; // UDP 통신 종류
        bool bClick;
        AnsiString asNetworkName;
        void __fastcall TransferData(BYTE ComKind, BYTE Add, BYTE *Data, DWORD DataLen, int Number, OPCode OPC);
        AnsiString __fastcall GetDNS(int iType);
        AnsiString __fastcall GetNetwork(int iType);
        unsigned short int __fastcall Calc_crc(unsigned char *buf, int numbytes);
        BYTE *__fastcall DLEDataConvert(BYTE *DLEData, int Size);
        int __fastcall GetDLEDataSize(BYTE *DLEData, int Size);
        AnsiString __fastcall Int2HexConvert(BYTE Value);
        void __fastcall RecieveData(char *Buf, int iBufSize);
public:		// User declarations
        char RedData[40960];
        char GreenData[40960];
        char BlueData[40960];
        int UDPClientPort;
        int UDPServerPort;
        AnsiString IPAddress;
        AnsiString DNSAddress;
        bool DNSConnect;
        bool bDisConnect;
        bool bStopSend;
        AnsiString PortUse1;
        BYTE BaudRateUse1;
        BYTE DTR1;
        BYTE RTS1;
        BYTE ComKind;
        AnsiString asDirectIPAddress[10];
        AnsiString LastMac;
        BYTE DirectIPIndex;
        BYTE DirectIPCnt;
        BYTE DabitNetTap;
        bool UDPUnicast;
        bool DB300Visible;
        bool bSerial;
        int iSerialPos;
        int iRemain;
        TColor MainColor; // 메인폼 배경색
        TColor MainDivideColor; // 메인폼 배경색
        TColor TextBGColor; // 텍스트 배경 배경색
        TColor TextColor; // 텍스트 폰트색
        TColor ButtonColor; // 버튼 배경색
        TColor MainRectangleColor;
        TColor CaptionPanelColor;
        AnsiString MainFont;
        BYTE  MainFontSize;
        AnsiString DefaultName;
        AnsiString DefaultIP;
        int DefaultPort;
        AnsiString DefaultSubnetMask;
        AnsiString DefaultGateway;
        AnsiString DefaultServerIP;
        int DefaultServerPort;
        bool __fastcall ComPortSet(BYTE ComKind, AnsiString PortUse, BYTE BaudRateUse, BYTE DTR, BYTE RTS, AnsiString IPAddress, int IPPort, bool bMessage);
        void __fastcall RoadIniFile();
        void __fastcall SaveIniFile();
        bool __fastcall ComClose(BYTE ComKind);
        void __fastcall ReadParsing(bool ComKind, char Add, BYTE *ParsingData, int ParsingSize);
        bool __fastcall ModuleData(BYTE ComKind, BYTE Addr, BYTE *Data, DWORD DataLen, int Number, OPCode OPC, bool bMessage);
        void __fastcall NoneDelayCount(int Cnt);
        void __fastcall LANDelayCount(int Cnt);
        void __fastcall SerialDelayCount(int Cnt);
        void __fastcall SearchComPort();
        bool __fastcall Apply();
        __fastcall TFormDABITNet(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormDABITNet *FormDABITNet;
//---------------------------------------------------------------------------
#endif
