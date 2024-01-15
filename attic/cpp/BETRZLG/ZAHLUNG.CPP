#include "stdafx.h"
#include "zahlung.h"

IMPLEMENT_SERIAL(CZahlung, CObject, 0)

CZahlung::CZahlung()
{
	ktonr = new CString();  
	bank = new CString();
	inhaber = new CString();
	betrag = 0;
	for (int i = 0; i < 6; i++)
		unr[i] = new CString();
}

CZahlung::CZahlung(const ZStruct& zs)
{
	ktonr = new CString(*zs.ktonr);
	bank = new CString(*zs.bank);
	inhaber = new CString(*zs.inhaber);
	
	betrag = zs.betrag;
	for (int i = 0; i < 6; i++)
	{
		if (!zs.unr[i]->IsEmpty())
			unr[i] = new CString(*zs.unr[i]);
		else
			unr[i] = new CString();
	}
}

CZahlung::~CZahlung()
{
	if (ktonr)   
	{
		delete ktonr;             
		ktonr = NULL;
	}
		
	for (int i = 0; i < 6; i++)
	{
		if (unr[i])  
		{
			delete unr[i];
			unr[i] = NULL;
		}
	}
}

void CZahlung::Serialize(CArchive& ar)
{
	if (ar.IsStoring())
	{
		ar << *ktonr << *bank << *inhaber << betrag;
		for (int i = 0; i < 6; i++)
			ar << *unr[i];	
	}
	else
	{
		ar >> *ktonr >> *bank >> *inhaber >> betrag;
		for (int i = 0; i < 6; i++)
			ar >> *unr[i];
	}       
}
	

