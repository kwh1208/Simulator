//---------------------------------------------------------------------------

#ifndef TFormLEDSetH
#define TFormLEDSetH
//---------------------------------------------------------------------------
#include <Classes.hpp>
#include <Controls.hpp>
#include <StdCtrls.hpp>
#include <Forms.hpp>
#include "TntStdCtrls.hpp"
#include "te_controls.hpp"
#include "TntExtCtrls.hpp"
#include <ExtCtrls.hpp>
#include "TntButtons.hpp"
#include <Buttons.hpp>
#include "TntComCtrls.hpp"
#include <ComCtrls.hpp>
//---------------------------------------------------------------------------
class TFormLEDSet : public TForm
{
__published:	// IDE-managed Components
        TTntPanel *pnMainDivide;
        TTntPanel *pnMain;
        TTntLabel *laDispType;
        TTntLabel *TntLabel2;
        TTntLabel *Label6;
        TTntLabel *laNotice;
        TTntLabel *TntLabel1;
        TTntListBox *lbDispType;
        TTntMemo *eUserNotice;
        TTntMemo *eNotice;
        TTntPanel *pnCaption;
        TTntPanel *pnBorderIcon;
        TTntSpeedButton *btnHide;
        TTntSpeedButton *btnMax;
        TTntSpeedButton *btnExit;
        TTntComboBox *cbDispColor;
        TTntComboBox *cbScanOder;
        TTntPanel *pnBSave;
        TTntSpeedButton *BSave;
        TTntPanel *pnDataSend;
        TTntSpeedButton *DataSend;
        TTntPanel *pnBitBtnOk;
        TTntSpeedButton *BitBtnOk;
        TTntPanel *pnDivide;
        TTntPanel *gbDetail;
        TTntPanel *pnDetail;
        TTntPanel *laDetail;
        TTntPanel *pnDataSendAuto;
        TTntSpeedButton *DataSendAuto;
        TTntEdit *eDelay;
        TTntUpDown *UpDownDelay;
        TTntLabel *TntLabel6;
        TTntPanel *pnBtnWrite;
        TTntSpeedButton *BtnWrite;
        TTntPanel *pnBtnRead;
        TTntSpeedButton *BtnRead;
        TTntEdit *eDetailDelayBefore;
        TTntUpDown *UpDownDetailDelayBefore;
        TTntLabel *TntLabel3;
        TTntEdit *eDetailDelayAfter;
        TTntUpDown *UpDownDetailDelayAfter;
        TTntLabel *TntLabel4;
        TTntLabel *TntLabel5;
        TTntLabel *TntLabel7;
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall lbDispTypeClick(TObject *Sender);
        void __fastcall lbDispTypeDrawItem(TWinControl *Control, int Index,
          TRect &Rect, TOwnerDrawState State);
        void __fastcall DataSendClick(TObject *Sender);
        void __fastcall BitBtnOkClick(TObject *Sender);
        void __fastcall BSaveClick(TObject *Sender);
        void __fastcall lbDispTypeDblClick(TObject *Sender);
        void __fastcall lbDispTypeKeyUp(TObject *Sender, WORD &Key,
          TShiftState Shift);
        void __fastcall BitBtnCancelClick(TObject *Sender);
        void __fastcall btnHideClick(TObject *Sender);
        void __fastcall btnMaxClick(TObject *Sender);
        void __fastcall btnExitClick(TObject *Sender);
        void __fastcall pnCaptionMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall BtnWriteClick(TObject *Sender);
        void __fastcall pnCaptionMouseMove(TObject *Sender,
          TShiftState Shift, int X, int Y);
private:	// User declarations
        BYTE LEDSetUserNotice[2][50][200];
        BYTE LEDSetColorOder[2][50];
        BYTE LEDSetScanOder[2][50];
        int OldXPos;
        int OldYPos;
        bool bCaptionMove;
public:		// User declarations
        __fastcall TFormLEDSet(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormLEDSet *FormLEDSet;
//---------------------------------------------------------------------------
#endif
