
#ifndef ProtocolH
#define ProtocolH
//------------------------------------------------------------------------------

enum ResponseSyn
{
        SynSDLEHead,
        SynSTXHead,
        SynSendID,
        SynAddress,
        SynHLen,
        SynLLen,
        SynOPCode,
        SynDataField,
        SynEDLEHead,
        SynETXHead,
        SynBcc,
        SynBlock,
        SynASCIISTX,
        SynASCIIAddr,
        SynASCIICheck,
        SynASCIICOMMCode,
        SynASCIIStatus,
        SynASCIIETXHead,
        SynASCIIETX,
        SynASCIIBCC,
};

enum HeadSyn
{
        STX = 0x02,
        ETX = 0x03,
        MTX = 0x05,
        DLE = 0x10
};

enum OPCode
{
        BS   = 0x40,      // Board Size Set                 OPCode(64)
        PC   = 0x41,      // Power Control                  OPCode(65)
        SC   = 0x42,      // Screen Clear                   OPCode(66)
        BC   = 0x44,      // 전광판 밝기                    OPCode(68)
        DC   = 0x45,      // Display Control                OPCode(69)
        CT   = 0x47,      // Current Time 전송              OPCode(71)
        FRW  = 0x48,      // Font Read Write                OPCode(72)
        DTS  = 0x49,      // 표출 Type Setting              OPCode(73)
        DFS  = 0x4a,      // DIBD Factory Rset              OPCode(74)
        FMMC = 0x4b,      // 일반문구 메모리 초기화         OPCode(75)
        FMI  = 0x4c,      // 일반문구 등록                  OPCode(76)
        ED   = 0x4d,      // 긴급메시지 삭제                OPCode(77)
        SO   = 0x4e,      // 신호출력                       OPCode(78)
        EDI  = 0x4f,      // 배경화면 이미지                OPCode(79)
        NOD  = 0x53,      // Nomarl File Data               OPCode(83)
        ND   = 0x54,      // Emergency File Data            OPCode(84)
        RFD  = 0x61,      // 현재 Scheudle 업로드           OPCode(97)
        RSD  = 0x62,      // 전체 Scheudle 업로드           OPCode(98)
        RID  = 0x63,      // Return Image 데이터            OPCode(99)
        RPD  = 0x65,      // Return Paramater 데이터        OPCode(101)
        RCT  = 0x66,      // Return 현제시간 업로드         OPCode(102)
        CVI  = 0x67,      // Controler Version Information  OPCode(103)
        CDSR = 0x69,      // CDS값  얻어오기                OPCode(105)
        DCT  = 0x6a,      // DIBD 접속테스트                OPCode(106)
        AN   = 0x88,      // Broad Cast 송신                OPCode(136)
        DS   = 0x89,      // DIBD Reset                     OPCode(130)
        DSI  = 0x8e,      // Display systemp Infor          OPCode(142)
        DSC  = 0x8f,      // DIBD Display Screen Control    OPCode(143)
        FED  = 0x94,      // Emergency File Data            OPCode(148)
        FND  = 0x95,      // Nomarl File Data               OPCode(149)
        FFNT = 0x98,      // 풀컬러 폰트 Data               OPCode(152)
        FDPI = 0x6b,      // 풀컬러 DIBD 제품 정보          OPCode(107)
        FI   = 0x6f,      // 펌웨어 업데이트 정보           OPCode(111)
        FU   = 0x9f,      // 펌웨어 업데이트                OPCode(159)
};

enum Status
{
        ACK = 0x06,
        NAK = 0x15,
        blank = 0x20
};

enum PowerOnOff
{
        PowerOn = 0x01,
        PowerOff = 0x00
};

enum eOnOff
{
        On = 0x01,
        Off = 0x00
};

enum Disp
{
        DispOn = 0x01,
        DispSend = 0x02,
        DispOff = 0x00,
        ProtocolDispSend = 0x10,
        ProtocolDispOn = 0x11,
};

enum eBaudRate
{
        BR2400 = 0x00,
        BR4800 = 0x01,
        BR9600 = 0x02,
        BR19200 = 0x03,
        BR38400 = 0x04,
        BR57600 = 0x05,
        BR115200 = 0x06
};

enum eFlow
{
        Disable = 0x00,
        Enable  = 0x01,
        Handshake = 0x02,
        Toggle = 0x03
};

enum eDirection
{
        Horizontal,
        Vertical1,
        Vertical2
};

enum eLEDModule
{
        e2BPP = 2,
        e3BPP = 3,
        e8BPP = 8,
        e16BPP = 16,
        e24BPP = 24,
};

enum TGEffectType
{
        etNormal,
        etRandom,
        etShift,                                                                
        etWipe,                                                                 
        etBlind,                                                                
        etCurtain,                                                              
        etScaleIn,
        etScaleOut,
        etRotate,
        etBlink,
        etBlinkReverse,
        et3DShift,
        etLineTest,
        etEqualizer,
};

enum TGEffectDirection
{
        edNone,
        edBrightUp,
        edBrightDown,
        edHoriMirror,                                                           
        edVertMirror,                                                           
        edLeft,                                                                 
        edRight,                                                                
        edUp,                                                                   
        edDown,                                                                 
        edLeftUp,
        edUpDown,
        edLeftAdd,
        edLeftDown,
        edRightUp,
        edRightDown,
        edCenter,
        edHoriSide,
        edHoriCenter,
        edVerSide,
        edVerCenter,         
        edHoriSideCenter,
        edVertSideCenter,
        edReverseClock,      
        edClock,
        edReverseClock2,     
        edClock2,
        edRed,
        edGreen,
        edBlue,
        edWhite,
        edRGB,
        edAll,
        edOrder,             
        edRandom,
        edStop,              
        edClear,
        edShiftLeft,
        edShiftRight,
        edShiftUp,
        edShiftDown
};

enum Effect
{
        NoEffect,
        None,
        BrightUp,
        BrightDown,
        HoriMirror,
        VertMirror,               
        ShiftLeft,                
        ShiftRight,
        ShiftUp,
        ShiftDown,
        ShiftLeftAdd,             
        ShiftUpDown,
        WipeLeft,                 
        WipeRight,
        WipeUp,
        WipeDown,                 
        WipeLeftUp,               
        WipeRightDown,
        BlindLeft,
        BlindRight,
        BlindUp,                  
        BlindDown,
        BlindLeftUp,
        BlindRightDown,
        CurtainHoriSide,
        CurtainHoriCenter,        
        CurtainVertSide,
        CurtainVertCenter,
        CurtainHoriSideCenter,
        CurtainVertSideCenter,
        ScaleInLeft,           
        ScaleInRight,
        ScaleInUp,
        ScaleInDown,
        ScaleInLeftUp,
        ScaleOutLeft,          
        ScaleOutRight,
        ScaleOutUp,
        ScaleOutDown,
        ScaleOutRightDown,
        RotateReverseClock,    
        RotateClock,
        Rotate2ReverseClock,
        Rotate2Clock,
        BlinkRed,
        BlinkGreen,            
        BlinkBlue,
        BlinkWhite,
        BlinkRGB,
        BlinkReverseRed,
        BlinkReverseGreen,     
        BlinkReverseBlue,
        BlinkReverseWhite,
        BlinkReverseRGB,
        BlinkReverseAll= 0x37,        //55
        Shift3DLeft = 0x36,
        Shift3DRight = 0x38,
        Shift3DUp = 0x39,
        Shift3DDown = 0x40,
        LineLeft = 0x73,
        LineRight = 0x70,
        LineUp = 0x71,
        LintDown = 0x74,
        LineLeftUp = 0x72,
        LineLeftDown = 0x76,
        LineRightUp = 0x77,
        LineRightDown = 0x75,
        Order = 0x79,
        Random = 0x7a,
        Equalizer = 0x7b,
};

struct sProtocolData
{
        BYTE ProtocolDispControl[51][3];
        BYTE ProtocolDispType[51][3];
        BYTE ProtocolCodeKind[51][3];
        BYTE ProtocolFontSize[51][3];
        BYTE ProtocolFontKind[51][3];
        BYTE ProtocolEntEffect[51][3][20];
        BYTE ProtocolEntDirect[51][3][20];
        BYTE ProtocolLasEffect[51][3][20];
        BYTE ProtocolLasDirect[51][3][20];
        BYTE ProtocolSpeed[51][3][20];
        BYTE ProtocolDelay[51][3][20];
        BYTE ProtocolStartXPos[51][3];
        BYTE ProtocolStartYPos[51][3];
        BYTE ProtocolEndXPos[51][3];
        BYTE ProtocolEndYPos[51][3];
        BYTE ProtocolBGImage[51][3];
        BYTE ProtocolColorType[51][3];
        BYTE ProtocolColor[51][3][500];
        BYTE ProtocolBGColor[51][3][500];
        BYTE ProtocolData[51][3][500];
};

struct sFontAddr
{
        BYTE FontPos[2];
        BYTE StartAddr[2];
        BYTE EndAddr[2];
        BYTE Width;
        BYTE Height;
};

struct sFontSData
{
        BYTE FontByteCnt[4];
        sFontAddr FontAddr[3];
};

struct sASCIIMessage
{
        BYTE MessageName[10];
        BYTE MessageData[330];
};

#pragma pack(push, 1)
typedef struct Response
{
        ResponseSyn CS;
        BYTE Add;
        BYTE HLen;
        BYTE LLen;
        BYTE SendID;
        BYTE OPCode;
        BYTE Status;
        BYTE Data[1024];
        BYTE TempData[10];
        BYTE Bcc[2];
        bool bDLE;
        int Rcnt;
        int TRcnt;
        int BCCcnt;
        int DELLen;
}Response, *LPResponse;
#pragma pack(pop)

#pragma pack(push, 1)
typedef struct _tsFontData
{
        BYTE FontCnt;
        sFontSData FontSData[4];
}tsFontData;
#pragma pack(pop)

#pragma pack(push, 1)
typedef struct FFontFileData
{
        HeadSyn SDLE;
        HeadSyn STX;
        BYTE Add;
        BYTE HLen;
        BYTE LLen;
        OPCode OPC;
        BYTE TotBlockCnt[2];
        BYTE BlockNum[2];
        BYTE FontBlockCnt[2];
        BYTE FontBlockNum[2];
        BYTE FileNum;
        BYTE Data[1024];
        BYTE BCC[2];
        HeadSyn EDLE;
        HeadSyn ETX;
}sFontFileData, *LPFontFileData;
#pragma pack(pop)

#pragma pack(push, 1)
typedef struct FLanguage
{
        WideString BtnPort;
        WideString BtnSize;
        WideString BtnApply;
        WideString BtnSend;
        WideString BtnTimeRead;
        WideString BtnSignalSend;
        WideString BtnSyncLED;
        WideString BtnPowerLEDOn;
        WideString BtnPowerLEDOff;
        WideString BtnClose;
        WideString BtnETC;
        WideString BtnLog;
        WideString BtnSelectAll;
        WideString BtnFontSend;
        WideString BtnLEDSetting;
        WideString BtnDIBDDownload;
        WideString BtnAdd;
        WideString BtnDelete;
        WideString BtnSave;
        WideString BtnModify;
        WideString BtnErase;
        WideString BtnReset;
        WideString BtnFReset;
        WideString BtnPSet;
        WideString SpeedButtonPreview;
        WideString BtnDABITOptionSetting;
        WideString BtnLanguage;
        WideString BtnProtocol;
        WideString BtnScreen;
        WideString BtnSetting;
        WideString BtnPacketRead;
        WideString FormComportCaption;
        WideString FormComportButton1;
        WideString FormComportButton2;
        WideString FormComportButton3;
        WideString FormComportButton4;
        WideString FormComportButton5;
        WideString FormComportrbSerial;
        WideString FormComportrbLAN;
        WideString FormComportrbServer;
        WideString FormComportrbUDP;
        WideString FormComportLabel1;
        WideString FormComportLabel2;
        WideString FormComportLabel3;
        WideString FormComportLabel4;
        WideString FormComportLabel5;
        WideString FormComportLabel6;
        WideString FormComportcbDDNS;
        WideString FormComportAddress;
        WideString FormComportSystem;
        WideString FormComportSearch;
        WideString FormComportRTime;
        WideString FormComportConnect;
        WideString FormProtocolCaption;
        WideString FormProtocolLabel1;
        WideString FormProtocolLabel2;
        WideString FormProtocolLabel3;
        WideString FormProtocolLabel4;
        WideString FormProtocolLabel5;
        WideString FormProtocolLabel6; 
        WideString FormProtocolLabel7;
        WideString FormProtocolLabel8; 
        WideString FormProtocolLabel9;
        WideString FormProtocolLabel10;
        WideString FormProtocolLabel11;
        WideString FormProtocolLabel12;
        WideString FormProtocolLabel13;
        WideString FormProtocolLabel14;
        WideString FormProtocolLabel15;
        WideString FormProtocolLabel16;
        WideString FormProtocolLabel17;
        WideString FormProtocolLabel18;
        WideString FormProtocolLabel19;
        WideString FormProtocolLabel20;
        WideString FormProtocolLabel21;
        WideString FormProtocolLabel22;
        WideString FormProtocolLabel23;
        WideString FormProtocolLabel24;
        WideString FormProtocolLabel25;
        WideString FormProtocolLabel26;
        WideString FormProtocolLabel27;
        WideString FormProtocolLabel28;
        WideString FormProtocolLabel29;
        WideString FormProtocolLabel30;
        WideString FormProtocolLabel31;
        WideString FormProtocolLabel32;
        WideString FormProtocolLabel33;
        WideString FormProtocolLabel34;
        WideString FormProtocolLabel35;
        WideString FormProtocolLabel36;
        WideString FormProtocolLabel37;
        WideString FormProtocolLabel38;
        WideString FormProtocolLabel39;
        WideString FormProtocolTab1;
        WideString FormProtocolTab2;
        WideString FormProtocolTab3;
        WideString FormProtocolEffect1;
        WideString FormProtocolEffect2;
        WideString FormProtocolEffect3;
        WideString FormProtocolEffect4;
        WideString FormProtocolEffect5;
        WideString FormProtocolEffect6;
        WideString FormProtocolEffect7;
        WideString FormProtocolEffect8;
        WideString FormProtocolEffect9;
        WideString FormProtocolEffect10;
        WideString FormProtocolEffect11;
        WideString FormProtocolDateItem1;
        WideString FormProtocolDateItem2;
        WideString FormProtocolDateItem3;
        WideString FormProtocolDateItem4;
        WideString FormProtocolDateItem5;
        WideString FormProtocolDateItem6;
        WideString FormProtocolDateItem7;
        WideString FormProtocolDirectItem1;
        WideString FormProtocolDirectItem2;
        WideString FormProtocolDirectItem3;
        WideString FormProtocolDirectItem4;
        WideString FormProtocolDirectItem5;
        WideString FormProtocolDirectItem6;
        WideString FormProtocolColor1;
        WideString FormProtocolColor2;
        WideString FormProtocolColor3;
        WideString FormProtocolColor4;
        WideString FormProtocolColor5;
        WideString FormProtocolColor6;
        WideString FormProtocolColor7;
        WideString FormProtocolColor8;
        WideString FormProtocolColor9;
        WideString FormFontCaption;
        WideString FormFontNameCaption;
        WideString FormFontLabel1;
        WideString FormFontLabel2;
        WideString FormFontLabel3;
        WideString FormFontLabel4;
        WideString FormFontLabel5;
        WideString FormFontLabel6;
        WideString FormFontLabel7;
        WideString FormFontItem[7];
        WideString FormLEDSetCaption;
        WideString FormLEDSetLabel1;
        WideString FormLEDSetLabel2;
        WideString FormLEDSetLabel3;
        WideString FormTransferCaption1;
        WideString FormTransferCaption2;
        WideString FormTransferCaption3;
        WideString FormTransferLabel1;
        WideString FormTransferLabel2;
        WideString FormLog;
        WideString FormETC;
        WideString FormFirmware;
        WideString FormASCIISet;
        WideString FormDABITOption;
        WideString FormBluetoothCaption;
        WideString FormBluetoothLabel1;
        WideString FormBluetoothLabel2;
        WideString FormBluetoothLabel3;
        WideString FormBluetoothButton1;
        WideString FormBluetoothButton2;
        WideString FormBluetoothButton3;
        WideString FormBluetoothButton4;
        WideString FormBluetoothButton5;
        WideString MessageCaption;
        WideString MessageButton01;
        WideString MessageButton02;
        WideString MessageButton03;
        WideString MessageButton04;
        WideString MessageButton05;
        WideString MessageButton06;
        WideString MessageButton07;
        WideString MessageButton08;
        WideString MessageButton09;
        WideString MessageButton10;
        WideString MessageButton11;
        WideString MessageButton12;
        WideString MessageButton13;
        WideString MessageButton14;
        WideString MessageButton15;
        WideString MessageButton16;
        WideString MessageButton17;
        WideString MessageButton18;
        WideString MessageButton19;
        WideString MessageButton20;
        WideString MessageButton21;
        WideString MessageButton22;
        WideString MessageButton23;
        WideString MessageButton24;
        WideString Message01;
        WideString Message02;
        WideString Message03;
        WideString Message04;
        WideString Message05;
        WideString Message06;
        WideString Message07;
        WideString Message08;
        WideString Message09;
        WideString Message10;
        WideString Message11;
        WideString Message12;
        WideString Message13;
        WideString Message14;
        WideString Message15;
        WideString Message16;
        WideString Message17;
        WideString Message18;
        WideString Message19;
        WideString Message20;
        WideString NoEffect;
        WideString EffectType1;
        WideString EffectType2;
        WideString EffectType3;
        WideString EffectType4;
        WideString EffectType5;
        WideString EffectType6;
        WideString EffectType7;
        WideString EffectType8;
        WideString EffectType9;
        WideString EffectType10;
        WideString EffectType11;
        WideString EffectType12;
        WideString EffectType13;
        WideString EffectType14;
        WideString EffectType15;
        WideString EffectType16;
        WideString EffectType17;
        WideString EffectType18;
        WideString EffectType19;
        WideString EffectType20;
        WideString DirType1;
        WideString DirType2;
        WideString DirType3;
        WideString DirType4;
        WideString DirType5;
        WideString DirType6;
        WideString DirType7;
        WideString DirType8;
        WideString DirType9;
        WideString DirType10;
        WideString DirType11;
        WideString DirType12;
        WideString DirType13;
        WideString DirType14;
        WideString DirType15;
        WideString DirType16;
        WideString DirType17;
        WideString DirType18;
        WideString DirType19;
        WideString DirType20;
        WideString DirType21;
        WideString DirType22;
        WideString DirType23;
        WideString DirType24;
        WideString DirType25;
        WideString DirType26;
        WideString DirType27;
        WideString DirType28;
        WideString DirType29;
        WideString DirType30;
        WideString DirType31;
        WideString DirType32;
        WideString DirType33;
        WideString DirType34;
        WideString DirType35;
        WideString DirType36;
        WideString DirType37;
        WideString DirType38;
        WideString DirType39;
        WideString DirType40;
        WideString DirType41;
        WideString DirType42;
        WideString DirType43;
        WideString DirType44;
        WideString DirType45;
        WideString DirType46;
        WideString DirType47;
        WideString DirType48;
        WideString DirType49;
        WideString DirType50;
}Language;
#pragma pack(pop)

static const int EffectTypeCount = 13;
static const int DirTypeCount = 46;
static const unsigned char Bit[8] = {0x01, 0x02, 0x04, 0x08, 0x10, 0x20, 0x40, 0x80};
//------------------------------------------------------------------------------
#endif
