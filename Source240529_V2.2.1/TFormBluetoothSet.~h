//---------------------------------------------------------------------------

#ifndef TFormBluetoothSetH
#define TFormBluetoothSetH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "TntButtons.hpp"
#include "TntExtCtrls.hpp"
#include "TntStdCtrls.hpp"
#include <Buttons.hpp>
#include <ExtCtrls.hpp>
#include "CPort.hpp"
#include "MMTimer.hpp"
//---------------------------------------------------------------------------
class TFormBluetoothSet : public TForm
{
__published:	// IDE-managed Components
        TTntPanel *pnMainDivide;
        TTntPanel *pnMain;
        TTntLabel *laTotFontSize;
        TTntPanel *gnBlutooth;
        TTntPanel *pnBlutooth;
        TTntLabel *laBTLabel1;
        TTntLabel *laBTLabel2;
        TTntLabel *laBTLabel3;
        TEdit *eBluetoothName;
        TEdit *eBluetoothPassword;
        TEdit *eBluetoothDelayTime;
        TTntPanel *pnCaption;
        TTntPanel *pnBorderIcon;
        TTntSpeedButton *btnHide;
        TTntSpeedButton *btnMax;
        TTntSpeedButton *btnExit;
        TTntPanel *pnDivide;
        TTntPanel *pnBitBtnOk;
        TTntSpeedButton *BitBtnOk;
        TTntPanel *pnBtnDelaySend;
        TTntSpeedButton *BtnDelaySend;
        TTntPanel *pnBtnSendSearch;
        TTntSpeedButton *BtnSendSearch;
        TTntPanel *pnBtnSendSet;
        TTntSpeedButton *BtnSendSet;
        TTntPanel *pnBtnSendBegin;
        TTntSpeedButton *BtnSendBegin;
        TTntPanel *pnBtnSendEnd;
        TTntSpeedButton *BtnSendEnd;
        TMMTimer *SerialMMTimer;
        TComPort *ComPort1;
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall BtnSendSearchClick(TObject *Sender);
        void __fastcall BitBtnOkClick(TObject *Sender);
        void __fastcall ComPort1RxChar(TObject *Sender, int Count);
        void __fastcall SerialMMTimerTimer(TObject *Sender);
        void __fastcall btnHideClick(TObject *Sender);
        void __fastcall btnMaxClick(TObject *Sender);
        void __fastcall btnExitClick(TObject *Sender);
        void __fastcall pnCaptionMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseMove(TObject *Sender,
          TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
private:	// User declarations
        int OldXPos;
        int OldYPos;
        bool bCaptionMove;
        char RedData[40960];
        int BluetoothIndex;
        void __fastcall ReadParsing(bool ComKind, char Add, BYTE *ParsingData, int ParsingSize);
        bool __fastcall ComPortSet(BYTE ComKind, AnsiString PortUse, BYTE BaudRateUse, BYTE DTR, BYTE RTS, AnsiString IPAddress, int IPPort, bool bMessage);
        bool __fastcall ComClose(BYTE ComKind);
public:		// User declarations
        bool bSerial;
        int iSerialPos;
        int iRemain;
        __fastcall TFormBluetoothSet(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormBluetoothSet *FormBluetoothSet;
//---------------------------------------------------------------------------
#endif
