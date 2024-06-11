package edu.monash.mevol;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MethodSigHelper {
	// now, update to get signature (with parameters) instead of just method name
	public static Set<String> getMethodSigNames(Set<MethodSig> methodSigs) {
		Set<String> methods = new HashSet<String>();

		for (MethodSig ms : methodSigs)
			methods.add(ms.getSignature());

		return methods;
	}

	public static Map<String, MethodSig> getMethodSigMap(Set<MethodSig> methodSigs) {
		Map<String, MethodSig> methodRepo = new HashMap<String, MethodSig>();

		for (MethodSig sig : methodSigs)
			methodRepo.put(sig.getSignature(), sig);

		return methodRepo;
	}
}
