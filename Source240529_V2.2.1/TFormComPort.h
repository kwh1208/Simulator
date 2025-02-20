//---------------------------------------------------------------------------

#ifndef TFormComPortH
#define TFormComPortH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "te_controls.hpp"
#include "TntStdCtrls.hpp"
#include "TntButtons.hpp"
#include <Buttons.hpp>
#include "TntExtCtrls.hpp"
#include <ExtCtrls.hpp>
#include "TFormBluetoothSet.h"
//---------------------------------------------------------------------------
class TFormComPort : public TForm
{
__published:	// IDE-managed Components
        TTntPanel *pnMainDivide;
        TTntPanel *pnMain;
        TTntPanel *pnCaption;
        TTntPanel *pnBorderIcon;
        TTntSpeedButton *btnHide;
        TTntSpeedButton *btnMax;
        TTntSpeedButton *btnExit;
        TTntRadioButton *rbSerial;
        TTntRadioButton *rbLAN;
        TTntRadioButton *rbServer;
        TTntPanel *pnBLEDSoftNet;
        TTntSpeedButton *BLEDSoftNet;
        TTntPanel *pnBPortCon;
        TTntSpeedButton *BPortCon;
        TTntPanel *pnBPortClose;
        TTntSpeedButton *BPortClose;
        TTntPanel *pnBitBtnOk;
        TTntSpeedButton *BitBtnOk;
        TTntRadioButton *rbUDP;
        TTntPanel *gbSerial;
        TTntPanel *pnSerial;
        TTeLabel *TeLabel1;
        TTeLabel *Label1;
        TTntComboBox *ComBox1;
        TTntComboBox *BaudBox1;
        TTntPanel *gbLAN;
        TTntPanel *pnLAN;
        TTntPanel *gbServer;
        TTntPanel *pnServer;
        TTntPanel *gbUDP;
        TTntPanel *pnUDP;
        TTntLabel *TntLabel4;
        TTntLabel *Label3;
        TEdit *Edit2;
        TEdit *Edit3;
        TTntLabel *TntLabel5;
        TTntLabel *TntLabel3;
        TEdit *Edit6;
        TTntLabel *TntLabel6;
        TTntLabel *TntLabel1;
        TEdit *Edit4;
        TEdit *Edit5;
        TTntLabel *TntLabel2;
        TTntPanel *pnDivide;
        TTntPanel *pnBSystem;
        TTntSpeedButton *BSystem;
        TTntLabel *laRTime;
        TTntComboBox *cbWaitTime;
        TTntLabel *TntLabel9;
        TTntPanel *pnBSearch;
        TTntSpeedButton *BSearch;
        TTntCheckBox *cbRS485;
        TTntLabel *laRS485;
        TTntPanel *pnBConnect;
        TTntSpeedButton *BConnect;
        TTntComboBox *cbDIBDAddress;
        TTntPanel *pnBBluetooth;
        TTntSpeedButton *BBluetooth;
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall rbComKind(TObject *Sender);
        void __fastcall BSystemClick(TObject *Sender);
        void __fastcall BPortCloseClick(TObject *Sender);
        void __fastcall BPortConClick(TObject *Sender);
        void __fastcall ComBox1ListBoxClick(TObject *Sender);
        void __fastcall ComBox1DropDown(TObject *Sender);
        void __fastcall BLEDSoftNetClick(TObject *Sender);
        void __fastcall BitBtnOkClick(TObject *Sender);
        void __fastcall btnHideClick(TObject *Sender);
        void __fastcall btnMaxClick(TObject *Sender);
        void __fastcall btnExitClick(TObject *Sender);
        void __fastcall pnCaptionMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall cbWaitTimeClick(TObject *Sender);
        void __fastcall BSearchClick(TObject *Sender);
        void __fastcall pnCaptionMouseMove(TObject *Sender,
          TShiftState Shift, int X, int Y);
        void __fastcall cbRS485Click(TObject *Sender);
        void __fastcall BConnectClick(TObject *Sender);
        void __fastcall cbDIBDAddressClick(TObject *Sender);
        void __fastcall BBluetoothClick(TObject *Sender);
private:	// User declarations
        int OldXPos;
        int OldYPos;
        bool bCaptionMove;
        void __fastcall SearchComPort();
public:		// User declarations
        __fastcall TFormComPort(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormComPort *FormComPort;
//---------------------------------------------------------------------------
#endif
