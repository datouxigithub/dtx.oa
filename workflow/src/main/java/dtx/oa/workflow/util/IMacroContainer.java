/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.util;

import dtx.oa.workflow.util.macro.DateCnMacro;
import dtx.oa.workflow.util.macro.DateCnShort1Macro;
import dtx.oa.workflow.util.macro.DateCnShort2Macro;
import dtx.oa.workflow.util.macro.DateCnShort3Macro;
import dtx.oa.workflow.util.macro.DateCnShort4Macro;
import dtx.oa.workflow.util.macro.DateTimeMacro;
import dtx.oa.workflow.util.macro.TimeMacro;
import dtx.oa.workflow.util.macro.WeekMacro;
import java.util.Stack;

/**
 *
 * @author datouxi
 */
public class IMacroContainer {
    private static final Stack<IMacro> macroStack=new Stack<>();
    static{
        macroStack.push(new DateCnMacro());
        macroStack.push(new DateCnMacro());
        macroStack.push(new DateCnShort1Macro());
        macroStack.push(new DateCnShort2Macro());
        macroStack.push(new DateCnShort3Macro());
        macroStack.push(new DateCnShort4Macro());
        macroStack.push(new DateTimeMacro());
        macroStack.push(new TimeMacro());
        macroStack.push(new WeekMacro());
    }
    
    public static IMacro chooseMacro(String type){
        Stack<IMacro> tmpStack=new Stack<>();
        while(!macroStack.isEmpty()){
            IMacro m=macroStack.peek();
            if(m.macroType().equals(type)){
                return m;
            }
            tmpStack.push(macroStack.pop());
        }
        while(!tmpStack.isEmpty())
            macroStack.push(tmpStack.pop());
        return null;
    }
}
