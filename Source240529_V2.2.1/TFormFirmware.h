//---------------------------------------------------------------------------

#ifndef TFormFirmwareH
#define TFormFirmwareH
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
//---------------------------------------------------------------------------
class TFormFirmware : public TForm
{
__published:	// IDE-managed Components
        TTntPanel *pnMainDivide;
        TTntPanel *pnMain;
        TTntLabel *Label1;
        TTntLabel *TeLabel1;
        TTntLabel *TeLabel2;
        TTntLabel *Label2;
        TTntLabel *TntLabel2;
        TEdit *ePath;
        TTntPanel *pnCaption;
        TTntPanel *pnBorderIcon;
        TTntSpeedButton *btnHide;
        TTntSpeedButton *btnMax;
        TTntSpeedButton *btnExit;
        TTntPanel *pnBVerCheck;
        TTntSpeedButton *BVerCheck;
        TTntPanel *pnFileOpen;
        TTntSpeedButton *FileOpen;
        TTntPanel *pnDataTransfer;
        TTntSpeedButton *DataTransfer;
        TTntPanel *pnBitBtnOk;
        TTntSpeedButton *BitBtnOk;
        TTntPanel *pnDivide;
        void __fastcall FormCreate(TObject *Sender);
        void __fastcall BVerCheckClick(TObject *Sender);
        void __fastcall DataTransferClick(TObject *Sender);
        void __fastcall BitBtnOkClick(TObject *Sender);
        void __fastcall FileOpenClick(TObject *Sender);
        void __fastcall btnHideClick(TObject *Sender);
        void __fastcall btnMaxClick(TObject *Sender);
        void __fastcall btnExitClick(TObject *Sender);
        void __fastcall pnCaptionMouseDown(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseUp(TObject *Sender,
          TMouseButton Button, TShiftState Shift, int X, int Y);
        void __fastcall pnCaptionMouseMove(TObject *Sender,
          TShiftState Shift, int X, int Y);
private:	// User declarations
        BYTE FirmwareKind;
        int OldXPos;
        int OldYPos;
        bool bCaptionMove;
public:		// User declarations
        int FirmwareBoardKind;
        int DIBDBoardKind;
        __fastcall TFormFirmware(TComponent* Owner);
};
//---------------------------------------------------------------------------
extern PACKAGE TFormFirmware *FormFirmware;
//---------------------------------------------------------------------------
#endif
