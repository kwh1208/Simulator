//---------------------------------------------------------------------------

#ifndef TFormDABITOptionH
#define TFormDABITOptionH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "te_controls.hpp"
#include "TntStdCtrls.hpp"
#include "TntButtons.hpp"
#include "TntExtCtrls.hpp"
#include <Buttons.hpp>
#include <ExtCtrls.hpp>
//---------------------------------------------------------------------------
class TFormDABITOption : public TForm
{
__published:	// IDE-managed Components
        TTntPanel *pnMainDivide;
        TTntPanel *pnMain;
        TTntPanel *pnDataSend;
        TTntSpeedButton *DataSend;
        TTntPanel *pnBitBtnOk;
        TTntSpeedButton *BitBtnOk;
        TTntPanel *pnDivide;
        TTntLabel *laLabel2;
        TTntLabel *laLabel3;
        TTntLabel *laLabel6;
        TTntLabel *laLabel1;
        TTntLabel *laLabel4;
        TTntLabel *laLabel5;
        TTntLabel *laLabel9;
        TTntLabel *laLabel10;
        TTntComboBox *cbDubug;
        TTntComboBox *cbBoardRate232;
        TTntComboBox *cbBH1;
        TTntComboBox *cbJ4;
        TTntPanel *pnCaption;
        TTntPanel *pnBorderIcon;
        TTntSpeedButton *btnHide;
        TTntSpeedButton *btnMax;
        TTntSpeedButton *btnExit;
        TTeButton *TeButton1;
        TTntPanel *gbSubCom;
        TTntPanel *pnSubCom;
        TTntRadioButton *rbWrite;
        TTntRadioButton *rbRead;
        TTntLabel *laLabel7;
        TTntComboBox *cbBoardRateTTL;
        TTntLabel *laLabel8;
        TTntComboBox *cbBoardRate485;
        TTntPanel *pnBtnComPort;
        TTntSpeedButton *BtnComPort2;
        TTeButton *BtnComPort;
        void __fastcall rbWriteClick(TObject *Sender);
        void __fastcall DataSendClick(TObject *Sender);
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall cbDubugClick(TObject *Sender);
        void __fastcall BitBtnOkClick(TObject *Sender);
        void __fastcall btnHideClick(TObject *Sender);
        void __fastcall btnMaxClick(TObject *Sender);
        void __fastcall btnExitClick(TObject *Sender);
        void __fastcall pnCaptionMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseMove(TObject *Sender,
          TShiftState Shift, int X, int Y);
        void __fastcall BtnComPortClick(TObject *Sender);
private:	// User declarations
        int OldXPos;
        int OldYPos;
        bool bCaptionMove;
public:		// User declarations
        __fastcall TFormDABITOption(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormDABITOption *FormDABITOption;
//---------------------------------------------------------------------------
#endif
