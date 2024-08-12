//---------------------------------------------------------------------------

#ifndef TFormMainH
#define TFormMainH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "CPort.hpp"
#include "te_controls.hpp"
#include "TntComCtrls.hpp"
#include "TntStdCtrls.hpp"
#include <ComCtrls.hpp>
#include "Protocol.h"
#include "TFormMessage.h"
#include "TFormTransfer.h"
#include "TDataModuleClient.h"
#include "TFormASCIISet.h"
#include "TFormSetting.h"
#include "TFormETC.h"
#include "TFormLog.h"
#include "TFormDABITNet.h"
#include "TFormDABITOption.h"
#include <IdAntiFreeze.hpp>
#include <IdAntiFreezeBase.hpp>
#include <IdBaseComponent.hpp>
#include <IdComponent.hpp>
#include <IdDNSResolver.hpp>
#include <IdIcmpClient.hpp>
#include <IdRawBase.hpp>
#include <IdRawClient.hpp>
#include <IdUDPBase.hpp>
#include <IdUDPClient.hpp>
#include <ScktComp.hpp>
#include <ExtCtrls.hpp>
#include <Menus.hpp>
#include "TntButtons.hpp"
#include "TntExtCtrls.hpp"
#include <Buttons.hpp>
#include <IdUDPServer.hpp>
#include <Dialogs.hpp>
#include "SUIURLLabel.hpp"
#include "HanConvert.hpp"
#include <AppEvnts.hpp>
#include "MMTimer.hpp"
//---------------------------------------------------------------------------
class TFormMain : public TForm
{
__published:	// IDE-managed Components
        TPanel *pnMainDivide;
        TTntBitBtn *BitBtnExit;
        TTePanel *ToolbarMain;
        TTeSpeedButton *NComPort;
        TTntPanel *TntPanel1;
        TsuiURLLabel *suiURLLabel1;
        TTePanel *ToobarSub;
        TsuiURLLabel *suiURLLabel2;
        TComPort *ComPort1;
        TIdDNSResolver *IdDNSResolver;
        TIdIcmpClient *IdIcmpClient1;
        TIdAntiFreeze *IdAntiFreeze1;
        TIdUDPClient *IdUDPClient;
        TIdUDPServer *IdUDPServer;
        TOpenDialog *OpenDialog;
        TServerSocket *ServerSocket1;
        TTimer *PreviewButtonTimer;
        THanConvert *HanConvert;
        TApplicationEvents *ApplicationEvents1;
        TTntPanel *pnMain;
        TTntPanel *pnCaption;
        TTntPanel *pnBorderIcon;
        TTntSpeedButton *btnHide;
        TTntSpeedButton *btnMax;
        TTntSpeedButton *btnExit;
        TTntPanel *pnLogo;
        TTntSpeedButton *btnLogo;
        TTntPanel *tnEnv;
        TTntLabel *Label21;
        TTntLabel *Label20;
        TTntLabel *Label18;
        TTntLabel *Label17;
        TTntLabel *Label15;
        TTntLabel *Label13;
        TTntLabel *Label24;
        TTntLabel *Label23;
        TTntLabel *Label16;
        TTntLabel *Label14;
        TTntLabel *Label12;
        TTntLabel *Label11;
        TTntLabel *Label10;
        TTntLabel *Label9;
        TTntLabel *Label8;
        TTntLabel *Label7;
        TTntLabel *Label6;
        TTntLabel *Label5;
        TTntLabel *Label4;
        TTntLabel *Label3;
        TTntLabel *Label2;
        TTntLabel *Label1;
        TTntLabel *Label19;
        TTntSplitter *TntSplitter1;
        TTntComboBox *cbFontKind;
        TTntRichEdit *eBGColor;
        TTntComboBox *cbBGImage;
        TTntComboBox *cbEndYPos;
        TTntComboBox *cbEndXPos;
        TTntComboBox *cbStartYPos;
        TTntComboBox *cbStartXPos;
        TTntComboBox *cbDelay;
        TTntComboBox *cbSpeed;
        TTntComboBox *cbAssistEffect;
        TTntComboBox *cbLasDirect;
        TTntComboBox *cbLasEffect;
        TTntComboBox *cbEntEffect;
        TTntComboBox *cbEntDirect;
        TTntComboBox *cbFontSize;
        TTntComboBox *cbCodeKind;
        TTntComboBox *cbDispType;
        TTntComboBox *cbDispControl;
        TTntComboBox *cbPageNo;
        TTntPanel *pnSpeedButtonPreview;
        TTntSpeedButton *SpeedButtonPreview;
        TTntPanel *tnSignAddr;
        TTntLabel *TntLabel9;
        TTntLabel *TntLabel8;
        TTntLabel *laAddress;
        TTntComboBox *cbWaitTime;
        TTntComboBox *cbDIBDAddress;
        TTntPanel *pntsButton;
        TTntPanel *tnASCIIMessage;
        TTntLabel *TntLabel4;
        TTntLabel *TntLabel7;
        TTntLabel *laDefault;
        TTntBitBtn *btnAdd;
        TTntBitBtn *btnDelete;
        TTntListBox *lbMesageList;
        TTntPanel *pnbtnSave1;
        TTntSpeedButton *btnSave1;
        TTntPanel *pnbtnSave2;
        TTntSpeedButton *btnSave2;
        TTntPanel *pnbtnSave3;
        TTntSpeedButton *btnSave3;
        TTntPanel *pnbtnSave4;
        TTntSpeedButton *btnSave4;
        TTntPanel *pnbtnSave5;
        TTntSpeedButton *btnSave5;
        TTntPanel *pnbtnSave6;
        TTntSpeedButton *btnSave6;
        TTntPanel *pnbtnSave7;
        TTntSpeedButton *btnSave7;
        TTntPanel *pnbtnSave8;
        TTntSpeedButton *btnSave8;
        TTntPanel *pnbtnSave9;
        TTntSpeedButton *btnSave9;
        TTntPanel *pnSBASCIIPreview;
        TTntSpeedButton *SBASCIIPreview;
        TTntPanel *pnBtnFactoryReset;
        TTntSpeedButton *BtnFactoryReset;
        TTntPanel *pnASCIISend1;
        TTntSpeedButton *ASCIISend1;
        TTntPanel *pnASCIISend2;
        TTntSpeedButton *ASCIISend2;
        TTntPanel *pnASCIISend3;
        TTntSpeedButton *ASCIISend3;
        TTntPanel *pnASCIISend4;
        TTntSpeedButton *ASCIISend4;
        TTntPanel *pnASCIISend5;
        TTntSpeedButton *ASCIISend5;
        TTntPanel *pnASCIISend6;
        TTntSpeedButton *ASCIISend6;
        TTntPanel *pnASCIISend7;
        TTntSpeedButton *ASCIISend7;
        TTntPanel *pnASCIISend8;
        TTntSpeedButton *ASCIISend8;
        TTntPanel *pnASCIISend9;
        TTntSpeedButton *ASCIISend9;
        TTntPanel *pnDivide;
        TTntPanel *rgPageNo;
        TTntPanel *pnPageNo;
        TTntRadioButton *rbPageNo1;
        TTntRadioButton *rbPageNo2;
        TTntPanel *rbTextNo;
        TTntPanel *pnTextNo;
        TTntRadioButton *rbNo1;
        TTntRadioButton *rbNo2;
        TTntRadioButton *rbNo3;
        TTntPanel *pnASCII;
        TTntSpeedButton *tsASCIIMessage;
        TTntSpeedButton *tsEnv;
        TTntPanel *pnDataSend;
        TTntSpeedButton *DataSend;
        TTntRichEdit *eColor;
        TTntRichEdit *eData;
        TTntPanel *tnInfor;
        TTntPanel *pnTapInfor;
        TTntSpeedButton *tsInfor;
        TPanel *pnLog;
        TRichEdit *RichEditLog;
        TTntPanel *pnLogCaption;
        TTntPanel *pnBtnLogButton;
        TTntSpeedButton *BtnLogClear;
        TTntPanel *pnICON;
        TTntSpeedButton *btnSetup;
        TTntSpeedButton *btnHelp;
        TTntPanel *pnBtnMsgErase;
        TTntSpeedButton *BtnMsgErase;
        TTntPanel *tnSpecial;
        TLabel *Label25;
        TTntPanel *gbMemInput;
        TTntPanel *pnMemInput;
        TTntPanel *pnBtnMemInput;
        TTntSpeedButton *BtnMemInput;
        TTntComboBox *cbMsgInput;
        TTntPanel *pnBitBtnPowerLEDOn;
        TTntSpeedButton *BitBtnPowerLEDOn;
        TTntPanel *pnBitBtnPowerLEDOff;
        TTntSpeedButton *BitBtnPowerLEDOff;
        TTntPanel *pnBtnTimeRead;
        TTntSpeedButton *BtnTimeRead;
        TTntPanel *pnBtnTimeSet;
        TTntSpeedButton *BtnTimeSet;
        TTntPanel *pnBtnFReset;
        TTntSpeedButton *BtnFReset;
        TTntPanel *gbBGImage;
        TTntPanel *pnBGImage;
        TTntPanel *gbSync;
        TTntPanel *pnSync;
        TTeLabel *SLabel1;
        TTeLabel *SLabel2;
        TTntComboBox *cbSoundSec1;
        TTntComboBox *cbSoundSec2;
        TTntPanel *pnBtnSendSync;
        TTntSpeedButton *BtnSendSync;
        TTntPanel *gbBright;
        TTntPanel *pnBright;
        TTntPanel *lapnBGImage;
        TTntPanel *lapnBright;
        TTntPanel *laSync;
        TTntPanel *laMemInput;
        TTntPanel *pnSpecial;
        TTntSpeedButton *tsSpecial;
        TTntPanel *pnBtnLog;
        TTntSpeedButton *BtnLog;
        TTntPanel *laSignSize;
        TTntPanel *gbSignSize;
        TTntPanel *pnSignSize;
        TTntLabel *TntLabel6;
        TTntLabel *TntLabel5;
        TTntLabel *TntLabel3;
        TTntLabel *TntLabel2;
        TTntLabel *TntLabel11;
        TTntLabel *TntLabel10;
        TTntLabel *TntLabel1;
        TTntPanel *pnBtnSignSize;
        TTntSpeedButton *BtnSignSize;
        TTntComboBox *cbProgramType;
        TTntComboBox *cbDirection;
        TTntCheckBox *cbDirectCheck;
        TTntUpDown *UpDownWidth;
        TTntUpDown *UpDownHeight;
        TTntEdit *TntEdit2;
        TTntEdit *TntEdit1;
        TTntPanel *gbSettings;
        TTntPanel *pnSettings;
        TTntPanel *laSettings;
        TTntPanel *pnBtnLEDSetting;
        TTntSpeedButton *BtnLEDSetting2;
        TTntPanel *pnBtnFontSend;
        TTntSpeedButton *BtnFontSend2;
        TTntPanel *pnBtnETC;
        TTntSpeedButton *BtnETC;
        TTntPanel *pnBtnDIBDDownload;
        TTntSpeedButton *BtnDIBDDownload2;
        TTntPanel *pnBtnDABITOption;
        TTntSpeedButton *BtnDABITOption2;
        TTntPanel *gbProtocol;
        TTntPanel *pnProtocol;
        TTntRadioButton *rgASCII;
        TTntRadioButton *rgHex;
        TTntPanel *laProgram;
        TTntPanel *pnBtnApply;
        TTntSpeedButton *BtnApply;
        TTntPanel *pnASCIISend10;
        TTntSpeedButton *ASCIISend10;
        TTntPanel *pnbtnSave10;
        TTntSpeedButton *btnSave10;
        TTntComboBox *cbMsgMemClear;
        TTntPanel *pnDivide2;
        TTntPanel *pnBtnMsgMemClear;
        TTntSpeedButton *BtnMsgMemClear;
        TTntLabel *TntLabel12;
        TTntLabel *TntLabel13;
        TTntLabel *laScreen;
        TTntComboBox *cbScreen;
        TTntPanel *pnBtnScreen;
        TTntSpeedButton *BtnScreen;
        TTntPanel *pnBtnBGImage;
        TTntSpeedButton *BtnBGImage;
        TTntComboBox *cbBGISelect;
        TTntLabel *laBGImage;
        TTntPanel *pnBtnBright;
        TTntSpeedButton *BtnBright;
        TTntComboBox *cbBright;
        TTntLabel *laLanguage;
        TTntComboBox *cbLanguage;
        TTntPanel *pnBtnComPort;
        TTntSpeedButton *BtnComPort2;
        TTntLabel *laProtocol;
        TTntComboBox *cbProtocol;
        TTntLabel *laBright;
        TTeButton *BtnComPort;
        TTeButton *BtnFontSend;
        TTeButton *BtnLEDSetting;
        TTeButton *BtnDABITOption;
        TTeButton *BtnDIBDDownload;
        TTntRichEdit *mASCIIText1;
        TTntRichEdit *mASCIIText2;
        TTntRichEdit *mASCIIText3;
        TTntRichEdit *mASCIIText4;
        TTntRichEdit *mASCIIText5;
        TTntRichEdit *mASCIIText6;
        TTntRichEdit *mASCIIText7;
        TTntRichEdit *mASCIIText8;
        TTntRichEdit *mASCIIText9;
        TTntRichEdit *mASCIIText10;
        TTntLabel *laHelp;
        TTntLabel *laRealTime;
        TTntComboBox *cbRealTime;
        TTntPanel *pnBtnRealTime;
        TTntSpeedButton *BtnRealTime;
        TTntPanel *pnBtnPacketRead;
        TTntSpeedButton *BtnPacketRead;
        TTntComboBox *cbSoundSec3;
        TTntComboBox *cbSoundSec4;
        TTeLabel *SLabel4;
        TTeLabel *SLabel3;
        TTntPanel *pnBtnReset;
        TTntSpeedButton *BtnReset;
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall BitBtnExitClick(TObject *Sender);
        void __fastcall ComPort1RxChar(TObject *Sender, int Count);
        void __fastcall FormCloseQuery(TObject *Sender, bool &CanClose);
        void __fastcall IdUDPClientStatus(TObject *ASender,
          const TIdStatus AStatus, const AnsiString AStatusText);
        void __fastcall IdUDPServerStatus(TObject *ASender,
          const TIdStatus AStatus, const AnsiString AStatusText);
        void __fastcall IdUDPServerUDPRead(TObject *Sender, TStream *AData,
          TIdSocketHandle *ABinding);
        void __fastcall cbEntEffectClick(TObject *Sender);
        void __fastcall cbEntEffectDropDown(TObject *Sender);
        void __fastcall cbEntDirectDropDown(TObject *Sender);
        void __fastcall cbLasEffectClick(TObject *Sender);
        void __fastcall cbLasEffectDropDown(TObject *Sender);
        void __fastcall cbLasDirectDropDown(TObject *Sender);
        void __fastcall rbTextNoClick(TObject *Sender);
        void __fastcall cbFontSizeClick(TObject *Sender);
        void __fastcall rbTextNoRoadClick(TObject *Sender);
        void __fastcall tsShow(TObject *Sender);
        void __fastcall DataSendClick(TObject *Sender);
        void __fastcall BtnSignSizeClick(TObject *Sender);
        void __fastcall sbLogClearClick(TObject *Sender);
        void __fastcall cbDIBDAddressClick(TObject *Sender);
        void __fastcall rbPageNoClick(TObject *Sender);
        void __fastcall btnAddClick(TObject *Sender);
        void __fastcall btnDeleteClick(TObject *Sender);
        void __fastcall btnSave1Click(TObject *Sender);
        void __fastcall ASCIISend1Click(TObject *Sender);
        void __fastcall lbMesageListClick(TObject *Sender);
        void __fastcall lbMesageListDrawItem(TWinControl *Control,
          int Index, TRect &Rect, TOwnerDrawState State);
        void __fastcall cbWaitTimeClick(TObject *Sender);
        void __fastcall ServerSocket1ClientConnect(TObject *Sender,
          TCustomWinSocket *Socket);
        void __fastcall ServerSocket1ClientDisconnect(TObject *Sender,
          TCustomWinSocket *Socket);
        void __fastcall ServerSocket1ClientError(TObject *Sender,
          TCustomWinSocket *Socket, TErrorEvent ErrorEvent,
          int &ErrorCode);
        void __fastcall ServerSocket1ClientRead(TObject *Sender,
          TCustomWinSocket *Socket);
        void __fastcall btnMsgEraseClick(TObject *Sender);
        void __fastcall eDataKeyDown(TObject *Sender, WORD &Key,
          TShiftState Shift);
        void __fastcall eColorKeyDown(TObject *Sender, WORD &Key,
          TShiftState Shift);
        void __fastcall SpeedButtonPreviewClick(TObject *Sender);
        void __fastcall PreviewButtonTimerTimer(TObject *Sender);
        void __fastcall SBASCIIPreviewClick(TObject *Sender);
        void __fastcall mASCIITextClick(TObject *Sender);
        void __fastcall BtnFactoryResetClick(TObject *Sender);
        void __fastcall tsASCIIMessageClick(TObject *Sender);
        void __fastcall btnSetupClick(TObject *Sender);
        void __fastcall btnHelpClick(TObject *Sender);
        void __fastcall btnHideClick(TObject *Sender);
        void __fastcall btnMaxClick(TObject *Sender);
        void __fastcall btnExitClick(TObject *Sender);
        void __fastcall ApplicationEvents1ShortCut(TWMKey &Msg,
          bool &Handled);
        void __fastcall pnCaptionMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall eBGColorKeyDown(TObject *Sender, WORD &Key,
          TShiftState Shift);
        void __fastcall BtnETCClick(TObject *Sender);
        void __fastcall BtnLogClick(TObject *Sender);
        void __fastcall BtnApplyClick(TObject *Sender);
        void __fastcall cbDirectCheckClick(TObject *Sender);
        void __fastcall TntLabel10Click(TObject *Sender);
        void __fastcall BtnLogClearClick(TObject *Sender);
        void __fastcall rgASCIIClick(TObject *Sender);
        void __fastcall BtnMemInputClick(TObject *Sender);
        void __fastcall BtnMsgMemClearClick(TObject *Sender);
        void __fastcall BtnSendSyncClick(TObject *Sender);
        void __fastcall BtnBrightClick(TObject *Sender);
        void __fastcall BitBtnPowerLEDClick(TObject *Sender);
        void __fastcall BtnTimeReadClick(TObject *Sender);
        void __fastcall BtnTimeSetClick(TObject *Sender);
        void __fastcall BtnFResetClick(TObject *Sender);
        void __fastcall cbLanguageClick(TObject *Sender);
        void __fastcall pnCaptionMouseMove(TObject *Sender,
          TShiftState Shift, int X, int Y);
        void __fastcall BtnComPortClick(TObject *Sender);
        void __fastcall BtnFontSendClick(TObject *Sender);
        void __fastcall BtnLEDSettingClick(TObject *Sender);
        void __fastcall BtnDABITOptionClick(TObject *Sender);
        void __fastcall BtnDIBDDownloadClick(TObject *Sender);
        void __fastcall BtnScreenClick(TObject *Sender);
        void __fastcall mASCIITextMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall laHelpClick(TObject *Sender);
        void __fastcall laHelpMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall laHelpMouseUp(TObject *Sender, TMouseButton Button,
          TShiftState Shift, int X, int Y);
        void __fastcall BtnRealTimeClick(TObject *Sender);
        void __fastcall mASCIITextKeyDown(TObject *Sender, WORD &Key,
          TShiftState Shift);
        void __fastcall mASCIITextKeyUp(TObject *Sender, WORD &Key,
          TShiftState Shift);
        void __fastcall eDataKeyUp(TObject *Sender, WORD &Key,
          TShiftState Shift);
        void __fastcall eBGColorKeyUp(TObject *Sender, WORD &Key,
          TShiftState Shift);
        void __fastcall eColorKeyUp(TObject *Sender, WORD &Key,
          TShiftState Shift);
        void __fastcall cbRealTimeClick(TObject *Sender);
        void __fastcall BtnPacketReadClick(TObject *Sender);
private:	// User declarations
        TFormMessage *FMessage; //메시지 폼
        TFormFont *FFont;     // 폰트 전송 폼
        TFormLEDSet *FLEDSet; // LED 모듈 종류 설정 폼
        TFormDABITOption *FDABITOption; // DABIT Option 설정 폼
        AnsiString Status; // 상태 체크
        Response RP; // 리시브 프로토콜 구조체
        BYTE FailData; // 리시브 데이터 에러 데이터
        int ProtocolTabKind; // 긴급/ASCII/특수 저장 탭
        int MessageKind; // 일반/긴급종류 저장
        int ProtocolPageNo2; // 일반문구 저장 페이지
        int ProtocolBlockNo; // 긴급문구 저장 섹션
        int ProtocolBlockNo2; // 일반무구 저장 섹션
        AnsiString OldLogDate; // 로그 마지막 저장 날짜
        AnsiString asJohap;
        int OldXPos;
        int OldYPos;
        int NewXPos;
        int NewYPos;
        bool bTextKeyDown;
        bool bPacketOpen;
        void __fastcall SendData(BYTE ComKind, BYTE Add, BYTE *Data, DWORD DataLen, int Number, OPCode OPC); // 데이터 프로토콜 전송 함수
        void __fastcall SendFontFileData(BYTE ComKind, BYTE Add, OPCode OPC, BYTE *Data, int TotBlockCnt, int BlockCnt, int FontTotBlockCnt, int FontBlockCnt, int Number, int ImgCnt); // 폰트 프로토콜 전송 함수
        void __fastcall SendASCIIData(BYTE ComKind, BYTE Add, BYTE *Data, DWORD DataLen); // ASCII 데이터 프로토콜 전송 함수
        void __fastcall ButtonEnabled(bool bEnabled); // 버튼 활성화 함수
        void __fastcall PreviewSaveIniFile();
        void __fastcall PreviewRoadIniFile();
        void __fastcall ShowSetting();
public:		// User declarations
        TFormMessage *formMessage; // 메시지 함수 폼
        TFormTransfer *FTransfer;
        TFormSetting *FSetting; // 고급설정 폼
        TFormASCIISet *FASCIISet;
        TFormETC *FETC;
        TFormComPort *FComPort; // 통신설정 폼
        TFormFirmware *FFirmware; // 펌웨어 전송창 폼
        TFormDABITNet *FDABITNet;
        TFormBluetoothSet * FBluetoothSet;
        TDataModuleClient *DMClient; // TCP/IP 클라이언트 소켓
        TCustomWinSocket *ServerClientSocket;  // TCP/IP 서버 소켓
        TIdSocketHandle *SH; // UDP 서버 소켓
        AnsiString DNSAddress; // DDNS 실제 IP 주소
        bool DNSConnect; // TCP/IP 소켓 연결 상태
        bool bDisConnect; // TCP/IP 소켓 끊어진 상태
        bool FlagCrcPacket; // CRC 적용 플래그
        bool FlagDlePacket; // DLE 적용 플래그
        AnsiString    PortUse1; // COM 포트 저장
        eBaudRate     BaudRateUse1; // COM BaudRate 저장
        int Controler1; // 전광판 어드레스 저장
        AnsiString    IPAddress; // TCP/IP 저정
        int IPPort; // TCP/IP Client 포트 저장
        int ServerPort; // TCP/IP Server 포트 저장
        AnsiString    UDPIPAddress; // UDP IP 저장
        int DSPPort; // UDP 전광판 포트 저장
        int SRCPort; // UDP DabitChe 포트 저장
        BYTE ComKind; // 통신 종류 저장
        bool bStopSend; // 전송 정지 플래그
        Language LangFile; // 언어 파일 구조체
        AnsiString BasePath; // 메인 폴더
        AnsiString DestSysPath; // System 폴더
        AnsiString DestLogPath; // Log 폴더
        AnsiString DestFontPath;// Font 폴더
        AnsiString DestFirmwarePath; // 펌웨어 폴더
        AnsiString VerMj[4];
        int ModuleWidth; // 전광판 가로 크기
        int ModuleHeight; // 전광판 세로 크기
        int ProgramType; // 전광판 컬러 종류
        int DisplayType; // LED 모듈 종류
        int ColorOder;   // RGB 색상 종류
        int ScanOder;    // 모듈 Scan 순서
        int Bright;   // 밝기 값
        int Lang; // 언어코드 옵션
        int iLanguage; // 언어 옵션
        int MessageCount; // 일반문구 최대 갯수
        int ProtocolPageNo; // 일반문구 저장 페이지
        AnsiString asUDPIPAddress; // 전송될 UDP IP
        AnsiString LastFirmwarePathFile; // 마지막 펌웨어 함수 파일 이름
        AnsiString    FontFileName[4][3]; // 폰트 파일 이름
        AnsiString    FontFileNameVersion[4][3]; // 폰트 파일 이름
        bool          FontCountCheck[4];  // 체크된 폰트
        int           FontIndex[4][3];    // 폰트종류
        int           LanDelayTime;       // 랜통신 딜레이 시간
        int           FontSizeIndex[4];    // 폰트크기종류
        sASCIIMessage ASCIIMessageData[20]; // ASCII 메시지 구조체
        AnsiString asLogMessage; // 로그 메시지
        bool SchBmpFile; // 리시브 데이터 확인 플래그
        bool DelayRun; // 리시브 데이터 수동 시간 플래그
        bool bCRCFail; // CRC 에러 확인 플래그
        bool bDataFail; // 에러 데이터 확인 플래그
        int PreviewMode;
        int ImageSendingBlockCount; //전송블럭갯수
        sProtocolData ProtocolData; // 긴급/일반 문구 저장 구조체
        sProtocolData ASCIIData; // ASCII 문구 저장 구조체
        WideString EffectType[EffectTypeCount]; // 효과 종류
        WideString DirType[DirTypeCount]; // 효과 방향
        AnsiString SpeedValue[22]; // 속도
        AnsiString DelayValue[20]; // 정지시간
        int ASCIIBlockNo; // ASCII문구 저장 블럭
        int ASCIIPageNo;  // ASCII문구 저장 페이지
        int PorotocolType; // Porotocol 전송 종류
        int Direct; // 전광판 표출 방향
        bool DirectCheck; // 전광판 표출 방향 활성화 체크
        int LogFileCnt; // 로그 파일 저장 카운트
        int WaitTime;   //리시브 데이터 대기 시간
        int DelayTime;  //리시브 데이터 수동 대기 시간
        int ServerCommOpenDelay; // TCP/IP 통시시 소켓이 연결 안될때 대기 시간
        bool bMsg; // 로그 메시지 보기 플래그
        TColor MainColor; // 메인폼 배경색
        TColor MainDivideColor; // 메인폼 배경색
        TColor TextBGColor; // 텍스트 배경 배경색
        TColor TextColor; // 텍스트 폰트색
        TColor ButtonColor; // 버튼 배경색
        TColor MainRectangleColor;
        TColor CaptionPanelColor;
        TColor CaptionTextColor;
        AnsiString MainFont;
        BYTE MainFontSize;
        int DetailDelayBefore;
        int DetailDelayAfter;
        int SignalAutoDelayTime;
        int RetryCnt;
        BYTE bOptionDubug;
        BYTE bOptionBoardRate232;
        BYTE bOptionBoardRateTTL;
        BYTE bOptionBoardRate485;
        BYTE bOptionBH1;
        BYTE bOptionJ4;
        bool bCaptionMove; // 캡션 Move 상태인지 확인 플래그
        bool bRS485;
        bool bFirst; // 처음 시작 플래그
        bool bLogviewall;
        BYTE bRealTimeIndex;
        AnsiString BluetoothName;
        AnsiString BluetoothPassword;
        int BluetoothDelayTime;
        AnsiString __fastcall VersionInfo(const AnsiString & sQuery);
        void __fastcall RoadIniFile(); // 저장된 정보 ini 파일에서 불러오는 함수
        void __fastcall SaveIniFile(); // ini 파일 저장 함수
        void __fastcall RoadLangFile(AnsiString aslang); // 언어파일 불러오는 함수
        void __fastcall ReadParsing(BYTE ComKind, char Add, BYTE *ParsingData, int ParsingSize); // 파싱 함수
        void __fastcall InputLang(); // 언어 적용할 함수
        bool __fastcall ModuleData(BYTE ComKind, BYTE Addr, BYTE *Data, DWORD DataLen, int Number, OPCode OPC, bool bMessage); // 일반 데이터 전송한 리시브 데이터 받는 판단 함수
        bool __fastcall ModuleFontPacketData(BYTE ComKind, BYTE Add, OPCode OPC, BYTE *Data, int TotBlockCnt, int BlockCnt, int FontTotBlockCnt, int FontBlockCnt, int Number, int ImgCnt);  // Font 데이터 전송한 리시브 데이터 받는 판단 함수
        bool __fastcall ModuleASCIIData(BYTE ComKind, BYTE Addr, BYTE *Data, DWORD DataLen, bool bMessage);  // ASCII 데이터 전송한 리시브 데이터 받는 판단 함수
        bool __fastcall ComPortSet(BYTE ComKind, AnsiString PortUse, BYTE BaudRateUse, BYTE DTR, BYTE RTS, AnsiString IPAddress, int IPPort, bool bMessage); // 통신 연결 함수
        bool __fastcall ComClose(BYTE ComKind); // 통신 닫는 함수
        void __fastcall DelayCount(int Cnt);// 통신 리시브 딜레이 함수
        void __fastcall NoneDelayCount(int Cnt); // 딜레이 함수
        void __fastcall LANDelayCount(int Cnt); // 통신 딜레이 함수
        AnsiString __fastcall GetDNS(); // PC DNS 읽어오는 함수
        unsigned short int __fastcall Calc_crc(unsigned char *buf, int numbytes); // CRC 함수
        AnsiString __fastcall Byte2HexConvert(BYTE Value); // Byte 값을 로 String Hex 변환 함수
        BYTE __fastcall Hex2ByteConvert(AnsiString Value); // String Hex 값을 Byte 로 변환 함수
        int __fastcall Message(WideString Mes, WideString wsYes, WideString wsNo, WideString wsCancel, int Width, int Height, int btnCnt, TColor FontColor); // 메시지 함수
        void __fastcall AddLog(AnsiString Msg, TColor Color); // 로그 표시 및 저장 함수
        void __fastcall DeleteOldLogFile(AnsiString SitePath); // 오래된 로그 삭제 함수
        int __fastcall FindItemIndex(AnsiString Data, int Col); // 효과,방향 인덱스 리턴 함수
        BYTE __fastcall InputSchEffect(TGEffectType eEff, TGEffectDirection dDirect); // 전광판 효과값 리턴 함수
        BYTE __fastcall SetDelayTime(int DelayTime); // 딜레이값 리턴 함수
        AnsiString __fastcall GetDay(int iDay); // 현재 요일 String 리턴 함수
        BYTE *__fastcall SetLEDModuleValue(BYTE bDispType, BYTE bDispColor, BYTE bScanOder);// LED 모듈 셋팅 함수
        AnsiString __fastcall GetLocalIP(); //현재 PC IP 얻어오는 함수
        bool __fastcall CurrenTimeSync(); // PC 현재시간 전송
        int __fastcall ReturnDay(TDateTime Time); // 요일 리턴 값 함수
        AnsiString __fastcall IntTo485Address(int Value); // 485 주소 리턴 값 함수
        TColor __fastcall GetColor(int iColor);
        bool __fastcall PacketParsing(BYTE * ParsingData, int iDataSize);
        WideString __fastcall TFormMain::UTF8ToANSI(const char *pszCode, int &length);
        AnsiString __fastcall Int2HexConvert(BYTE Value);
        __fastcall TFormMain(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormMain *FormMain;
//---------------------------------------------------------------------------
#endif
